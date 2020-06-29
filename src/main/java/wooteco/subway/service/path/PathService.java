package wooteco.subway.service.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.line.LineStations;
import wooteco.subway.domain.path.PathType;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.service.path.dto.PathResponse;
import wooteco.subway.service.station.dto.StationResponse;

@Transactional(readOnly = true)
@Service
public class PathService {
    private final StationRepository stationRepository;
    private final LineRepository lineRepository;
    private final GraphService graphService;

    public PathService(StationRepository stationRepository, LineRepository lineRepository, GraphService graphService) {
        this.stationRepository = stationRepository;
        this.lineRepository = lineRepository;
        this.graphService = graphService;
    }

    public PathResponse findPath(String source, String target, PathType type) {
        if (Objects.equals(source, target)) {
            throw new RuntimeException();
        }

        List<Line> lines = lineRepository.findAllWithStations();
        Station sourceStation = stationRepository.findByName(source).orElseThrow(RuntimeException::new);
        Station targetStation = stationRepository.findByName(target).orElseThrow(RuntimeException::new);

        List<Station> path = graphService.findPath(lines, sourceStation, targetStation, type);

        List<LineStation> lineStations = lines.stream()
            .flatMap(it -> it.getStations().stream())
            .filter(it -> Objects.nonNull(it.getPreStation()))
            .collect(Collectors.toList());

        LineStations paths = new LineStations(extractPathLineStation(path, lineStations));
        int duration = paths.getTotalDuration();
        int distance = paths.getTotalDistance();

        return new PathResponse(StationResponse.listOf(path), duration, distance);
    }

    private List<LineStation> extractPathLineStation(List<Station> path, List<LineStation> lineStations) {
        Station preStation = null;
        List<LineStation> paths = new ArrayList<>();

        for (Station station : path) {
            if (preStation == null) {
                preStation = station;
                continue;
            }

            Station finalPreStation = preStation;
            LineStation lineStation = lineStations.stream()
                .filter(it -> it.isLineStationOf(finalPreStation.getId(), station.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

            paths.add(lineStation);
            preStation = station;
        }

        return paths;
    }
}
