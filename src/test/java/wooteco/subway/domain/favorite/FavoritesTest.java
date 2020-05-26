package wooteco.subway.domain.favorite;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FavoritesTest {

    @DisplayName("즐겨 찾기 목록에 포함되어 있는 모든 역의 ID들을 반환")
    @Test
    void findAllIds() {
        Set<Favorite> favorites = new HashSet<>(Arrays.asList(
            new Favorite(1L, 2L),
            new Favorite(1L, 3L),
            new Favorite(2L, 3L),
            new Favorite(4L, 1L)
        ));
        Set<Long> actual = new Favorites(favorites).findAllIds();
        HashSet<Long> expected = new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L));

        assertThat(actual).isEqualTo(expected);
    }
}