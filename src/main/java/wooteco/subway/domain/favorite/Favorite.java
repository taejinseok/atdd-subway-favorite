package wooteco.subway.domain.favorite;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.util.BaseEntity;
import wooteco.subway.exception.SameSourceTargetStationException;

@Entity
public class Favorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_STATION_ID", nullable = false, updatable = false)
    private Station sourceStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_STATION_ID", nullable = false, updatable = false)
    private Station targetStation;

    protected Favorite() {
    }

    public Favorite(Member member, Station sourceStation, Station targetStation) {
        this(null, member, sourceStation, targetStation);
    }

    public Favorite(Long id, Member member, Station sourceStation, Station targetStation) {
        validateSameStation(sourceStation, targetStation);
        this.id = id;
        this.member = member;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }

    private void validateSameStation(Station sourceStation, Station targetStation) {
        if (Objects.equals(sourceStation, targetStation)) {
            throw new SameSourceTargetStationException();
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public Station getTargetStation() {
        return targetStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Favorite favorite = (Favorite)o;
        return Objects.equals(id, favorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

