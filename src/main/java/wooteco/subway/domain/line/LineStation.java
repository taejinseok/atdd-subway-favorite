package wooteco.subway.domain.line;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.util.BaseEntity;

@Entity
public class LineStation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_STATION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRE_STATION_ID")
    private Station preStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATION_ID", nullable = false)
    private Station station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINE_ID", nullable = false)
    private Line line;

    @Column(nullable = false)
    private int distance;

    @Column(nullable = false)
    private int duration;

    protected LineStation() {
    }

    public LineStation(Station preStation, Station station, Line line, int distance, int duration) {
        this(null, preStation, station, line, distance, duration);
    }

    public LineStation(Long id, Station preStation, Station station, Line line, int distance, int duration) {
        this.id = id;
        this.preStation = preStation;
        this.station = station;
        this.line = line;
        this.distance = distance;
        this.duration = duration;
    }

    public void updatePreLineStation(Station preStation) {
        this.preStation = preStation;
    }

    public boolean isSameStationId(Long stationId) {
        return station.hasId(stationId);
    }

    public boolean isLineStationOf(Long preStation, Long station) {
        return this.preStation.hasId(preStation) && this.station.hasId(station)
            || this.preStation.hasId(station) && this.station.hasId(preStation);
    }

    public void changeLine(Line line) {
        this.line = line;
    }

    public Station getStation() {
        return station;
    }

    public Station getPreStation() {
        return preStation;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LineStation that = (LineStation)o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
