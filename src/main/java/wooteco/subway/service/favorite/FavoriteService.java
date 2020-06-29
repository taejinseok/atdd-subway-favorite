package wooteco.subway.service.favorite;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.favorite.Favorite;
import wooteco.subway.domain.favorite.FavoriteRepository;
import wooteco.subway.domain.member.Member;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.domain.station.Stations;
import wooteco.subway.exception.FavoriteNotFoundException;
import wooteco.subway.exception.StationNotFoundException;
import wooteco.subway.service.favorite.dto.FavoriteExistenceResponse;
import wooteco.subway.service.favorite.dto.FavoriteRequest;
import wooteco.subway.service.favorite.dto.FavoriteResponse;

@Transactional
@Service
public class FavoriteService {
    private final StationRepository stationRepository;
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(StationRepository stationRepository, FavoriteRepository favoriteRepository) {
        this.stationRepository = stationRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavorites(Member member) {
        return favoriteRepository.findByMember(member).stream()
            .map(favorite -> FavoriteResponse.of(
                favorite.getSourceStation(), favorite.getTargetStation()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FavoriteExistenceResponse hasFavoritePath(Member member, Long sourceStationId, Long targetStationId) {
        Stations stations = new Stations(
            stationRepository.findAllById(Arrays.asList(sourceStationId, targetStationId)));
        Station source = stations.extractStationById(sourceStationId).orElseThrow(StationNotFoundException::new);
        Station target = stations.extractStationById(targetStationId).orElseThrow(StationNotFoundException::new);
        boolean isExistFavoriteForMember = favoriteRepository.existsByMemberAndStations(member, source, target);
        return new FavoriteExistenceResponse(isExistFavoriteForMember);
    }

    public Long addFavorite(Member member, FavoriteRequest favoriteRequest) {
        Long sourceStationId = favoriteRequest.getSourceStationId();
        Long targetStationId = favoriteRequest.getTargetStationId();
        Stations stations = new Stations(
            stationRepository.findAllById(Arrays.asList(sourceStationId, targetStationId)));

        Station sourceStation = stations.extractStationById(sourceStationId).orElseThrow(NoSuchElementException::new);
        Station targetStation = stations.extractStationById(targetStationId).orElseThrow(NoSuchElementException::new);

        Favorite savedFavorite = favoriteRepository.save(new Favorite(member, sourceStation, targetStation));
        return savedFavorite.getId();
    }

    public void removeFavorite(Member member, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(FavoriteNotFoundException::new);
        if (!Objects.equals(member, favorite.getMember())) {
            throw new IllegalArgumentException();
        }
        favoriteRepository.delete(favorite);
    }
}
