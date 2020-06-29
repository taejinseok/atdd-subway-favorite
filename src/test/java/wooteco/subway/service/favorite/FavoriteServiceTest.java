package wooteco.subway.service.favorite;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.list;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wooteco.subway.domain.favorite.Favorite;
import wooteco.subway.domain.favorite.FavoriteRepository;
import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.service.favorite.dto.FavoriteRequest;
import wooteco.subway.service.favorite.dto.FavoriteResponse;
import wooteco.subway.service.station.dto.StationResponse;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {
    private static final Long FIRST_STATION_ID = 1L;
    private static final Long SECOND_STATION_ID = 2L;
    private static final Long THIRD_STATION_ID = 3L;

    private static final Station FIRST_STATION = new Station(FIRST_STATION_ID, "강남");
    private static final Station SECOND_STATION = new Station(SECOND_STATION_ID, "강북");
    private static final Station THIRD_STATION = new Station(THIRD_STATION_ID, "강동");
    private static final Member MEMBER = new Member(1L, "sample@sample", "sample", "sample");

    @Mock
    FavoriteRepository favoriteRepository;

    @Mock
    StationRepository stationRepository;

    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteService(stationRepository, favoriteRepository);
    }

    @DisplayName("회원의 즐겨찾기 노선 목록 전체를 조회")
    @Test
    void getFavorites() {
        when(favoriteRepository.findByMember(MEMBER)).thenReturn(Arrays.asList(
            new Favorite(MEMBER, FIRST_STATION, SECOND_STATION),
            new Favorite(MEMBER, SECOND_STATION, THIRD_STATION)
        ));
        List<FavoriteResponse> favorites = favoriteService.getFavorites(MEMBER);

        List<FavoriteResponse> expected = Arrays.asList(
            new FavoriteResponse(StationResponse.of(FIRST_STATION), StationResponse.of(SECOND_STATION)),
            new FavoriteResponse(StationResponse.of(SECOND_STATION), StationResponse.of(THIRD_STATION)));

        assertThat(favorites).isEqualTo(expected);
    }

    @DisplayName("해당 경로가 즐겨찾기에 추가되어있는지 확인")
    @Test
    void hasFavoritePath() {
        when(stationRepository.findAllById(Arrays.asList(FIRST_STATION_ID, SECOND_STATION_ID)))
            .thenReturn(Lists.list(FIRST_STATION, SECOND_STATION));
        favoriteService.hasFavoritePath(MEMBER, FIRST_STATION_ID, SECOND_STATION_ID);
        verify(favoriteRepository).existsByMemberAndStations(MEMBER, FIRST_STATION, SECOND_STATION);
    }

    @DisplayName("즐겨찾기 경로 추가")
    @Test
    void addFavorite() {
        when(stationRepository.findAllById(anyList())).thenReturn(list(FIRST_STATION, THIRD_STATION));
        when(favoriteRepository.save(any())).thenReturn(new Favorite(1L, MEMBER, FIRST_STATION, THIRD_STATION));
        FavoriteRequest request = new FavoriteRequest(FIRST_STATION_ID, THIRD_STATION_ID);
        favoriteService.addFavorite(MEMBER, request);
        verify(favoriteRepository).save(any());
    }

    @DisplayName("즐겨찾기에서 경로 삭제")
    @Test
    void removeFavorite() {
        when(favoriteRepository.findById(anyLong())).thenReturn(
            Optional.of(new Favorite(1L, MEMBER, FIRST_STATION, SECOND_STATION)));
        favoriteService.removeFavorite(MEMBER, 1L);
        verify(favoriteRepository).delete(any());
    }
}