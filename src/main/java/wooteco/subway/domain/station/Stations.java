package wooteco.subway.domain.station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Stations {
    private final List<Station> stations;

    public Stations(List<Station> stations) {
        this.stations = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(stations)));
    }

    public Optional<Station> extractStationById(Long stationId) {
        return stations.stream()
            .filter(it -> Objects.equals(it.getId(), stationId))
            .findFirst();
    }
}
