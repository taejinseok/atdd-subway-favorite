package wooteco.subway.domain.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import wooteco.subway.domain.station.Station;

@Embeddable
public class LineStations {
    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LineStation> stations;

    protected LineStations() {
        stations = new ArrayList<>();
    }

    public LineStations(Collection<LineStation> lineStations) {
        this.stations = new ArrayList<>(lineStations);
    }

    public List<LineStation> getStations() {
        return stations;
    }

    public void add(LineStation targetLineStation) {
        updatePreStationOfNextLineStation(targetLineStation.getPreStation(), targetLineStation.getStation());
        stations.add(targetLineStation);
    }

    private void remove(LineStation targetLineStation) {
        updatePreStationOfNextLineStation(targetLineStation.getStation(), targetLineStation.getPreStation());
        stations.remove(targetLineStation);
    }

    public void removeById(Long targetStationId) {
        extractByStationId(targetStationId)
            .ifPresent(this::remove);
    }

    public List<Station> getStationIds() {
        List<Station> result = new ArrayList<>();
        extractNext(null, result);
        return result;
    }

    private void extractNext(Station preStation, List<Station> stations) {
        this.stations.stream()
            .filter(it -> Objects.equals(it.getPreStation(), preStation))
            .findFirst()
            .ifPresent(it -> {
                Station nextStation = it.getStation();
                stations.add(nextStation);
                extractNext(nextStation, stations);
            });
    }

    private void updatePreStationOfNextLineStation(Station targetStation, Station newPreStation) {
        extractByPreStationId(targetStation)
            .ifPresent(it -> it.updatePreLineStation(newPreStation));
    }

    private Optional<LineStation> extractByStationId(Long stationId) {
        return stations.stream()
            .filter(it -> it.isSameStationId(stationId))
            .findFirst();
    }

    private Optional<LineStation> extractByPreStationId(Station preStation) {
        return stations.stream()
            .filter(it -> Objects.equals(it.getPreStation(), preStation))
            .findFirst();
    }

    public int getTotalDistance() {
        return stations.stream().mapToInt(LineStation::getDistance).sum();
    }

    public int getTotalDuration() {
        return stations.stream().mapToInt(LineStation::getDuration).sum();
    }
}
