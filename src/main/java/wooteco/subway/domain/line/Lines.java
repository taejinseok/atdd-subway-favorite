package wooteco.subway.domain.line;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import wooteco.subway.domain.station.Station;

public class Lines {
    private final List<Line> lines;

    public Lines(List<Line> lines) {
        this.lines = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(lines)));
    }

    public List<Station> getStations() {
        return lines.stream()
            .flatMap(line -> line.getStations().stream())
            .map(LineStation::getStation)
            .collect(toList());
    }

    public List<Line> getLines() {
        return lines;
    }
}
