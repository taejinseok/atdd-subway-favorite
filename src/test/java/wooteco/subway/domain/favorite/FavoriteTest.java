package wooteco.subway.domain.favorite;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.station.Station;
import wooteco.subway.exception.SameSourceTargetStationException;

class FavoriteTest {
    private static final Member MEMBER = new Member(1L, "sample", "name", "pass");
    private static final Station FIRST_STATION = new Station(1L, "first");
    private static final Station SECOND_STATION = new Station(2L, "second");

    @DisplayName("출발역과 도착역이 다른 즐겨찾기 경로 인스턴스가 정상적으로 생성된다.")
    @Test
    void name1() {
        assertThatCode(() -> new Favorite(MEMBER, FIRST_STATION, SECOND_STATION))
            .doesNotThrowAnyException();
    }

    @DisplayName("출발역과 도착역이 같은 즐겨찾기 경로 인스턴스 생성시 SameSourceTargetStationException 예외를 발생시킨다.")
    @Test
    void name() {
        assertThatThrownBy(() -> new Favorite(MEMBER, FIRST_STATION, FIRST_STATION))
            .isInstanceOf(SameSourceTargetStationException.class);
    }
}