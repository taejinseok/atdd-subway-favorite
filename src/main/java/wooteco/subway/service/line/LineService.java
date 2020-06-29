package wooteco.subway.service.line;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.domain.station.Stations;
import wooteco.subway.service.line.dto.LineDetailResponse;
import wooteco.subway.service.line.dto.LineRequest;
import wooteco.subway.service.line.dto.LineStationCreateRequest;
import wooteco.subway.service.line.dto.WholeSubwayResponse;

@Transactional
@Service
public class LineService {
    private final LineStationService lineStationService;
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;

    public LineService(LineStationService lineStationService, LineRepository lineRepository,
        StationRepository stationRepository) {
        this.lineStationService = lineStationService;
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
    }

    public Line save(Line line) {
        return lineRepository.save(line);
    }

    @Transactional(readOnly = true)
    public List<Line> findLines() {
        return lineRepository.findAll();
    }

    public void updateLine(Long id, LineRequest request) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(request.toLine());
        lineRepository.save(persistLine);
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public void addLineStation(Long id, LineStationCreateRequest request) {
        Line line = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        Long preStationId = request.getPreStationId();
        Long stationId = request.getStationId();
        Stations stations = new Stations(
            stationRepository.findAllById(Arrays.asList(preStationId, stationId)));

        Station preStation = stations.extractStationById(preStationId).orElse(null);
        Station station = stations.extractStationById(stationId).orElseThrow(NoSuchElementException::new);

        LineStation lineStation = new LineStation(preStation, station, line,
            request.getDistance(), request.getDuration());

        line.addLineStation(lineStation);
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = lineRepository.findById(lineId).orElseThrow(RuntimeException::new);
        line.removeLineStationById(stationId);
    }

    @Transactional(readOnly = true)
    public LineDetailResponse retrieveLine(Long id) {
        return lineStationService.findLineWithStationsById(id);
    }

    @Transactional(readOnly = true)
    public WholeSubwayResponse findLinesWithStations() {
        return lineStationService.findLinesWithStations();
    }
}
