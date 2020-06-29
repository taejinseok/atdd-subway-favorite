package wooteco.subway.domain.favorite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.station.Station;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByMember(Member member);

    @Query(value = "select case when (count(f) > 0) then true else false end from Favorite f where f.member = :member and f.sourceStation = :sourceStation and f.targetStation = :targetStation")
    boolean existsByMemberAndStations(@Param("member") Member member, @Param("sourceStation") Station sourceStation,
        @Param("targetStation") Station targetStation);
}
