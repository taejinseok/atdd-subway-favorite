package wooteco.subway.service.line;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.Lines;
import wooteco.subway.domain.station.Station;
import wooteco.subway.service.line.dto.LineDetailResponse;
import wooteco.subway.service.line.dto.WholeSubwayResponse;

@Transactional
@Service
public class LineStationService {
    private final LineRepository lineRepository;

    public LineStationService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Transactional(readOnly = true)
    public LineDetailResponse findLineWithStationsById(Long lineId) {
        Line line = lineRepository.findById(lineId).orElseThrow(RuntimeException::new);
        List<Station> stations = line.getStationIds();

        return LineDetailResponse.of(line, stations);
    }

    @Transactional(readOnly = true)
    public WholeSubwayResponse findLinesWithStations() {
        Lines lines = new Lines(lineRepository.findAll());

        List<LineDetailResponse> lineDetailResponses = lines.getLines().stream()
            .map(line -> LineDetailResponse.of(line, line.getStationIds()))
            .collect(Collectors.toList());

        return WholeSubwayResponse.of(lineDetailResponses);
    }

    public void deleteLineStationByStationId(Long stationId) {
        List<Line> lines = lineRepository.findAll();
        lines.forEach(line -> line.removeLineStationById(stationId));
    }
}
