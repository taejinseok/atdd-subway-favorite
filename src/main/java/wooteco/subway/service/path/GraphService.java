package wooteco.subway.service.path;

import java.util.List;
import java.util.Objects;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.path.PathType;
import wooteco.subway.domain.station.Station;

@Transactional(readOnly = true)
@Service
public class GraphService {
    public List<Station> findPath(List<Line> lines, Station source, Station target, PathType type) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
            = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        lines.stream()
            .flatMap(line -> line.getStationIds().stream())
            .forEach(graph::addVertex);

        lines.stream()
            .flatMap(line -> line.getStations().stream())
            .filter(lineStation -> Objects.nonNull(lineStation.getPreStation()))
            .forEach(lineStation -> graph.setEdgeWeight(
                graph.addEdge(lineStation.getPreStation(), lineStation.getStation()),
                type.findWeightOf(lineStation)));

        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        return dijkstraShortestPath.getPath(source, target).getVertexList();
    }
}
