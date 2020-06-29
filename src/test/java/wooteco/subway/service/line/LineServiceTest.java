package wooteco.subway.service.line;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;
import wooteco.subway.service.line.dto.LineStationCreateRequest;

@ExtendWith(MockitoExtension.class)
public class LineServiceTest {
    private static final String STATION_NAME1 = "강남역";
    private static final String STATION_NAME2 = "역삼역";
    private static final String STATION_NAME3 = "선릉역";
    private static final String STATION_NAME4 = "삼성역";

    @Mock
    private LineRepository lineRepository;
    @Mock
    private LineStationService lineStationService;
    @Mock
    private StationRepository stationRepository;

    private LineService lineService;

    private Line line;
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;

    @BeforeEach
    void setUp() {
        lineService = new LineService(lineStationService, lineRepository, stationRepository);

        station1 = new Station(1L, STATION_NAME1);
        station2 = new Station(2L, STATION_NAME2);
        station3 = new Station(3L, STATION_NAME3);
        station4 = new Station(4L, STATION_NAME4);

        line = new Line(1L, "2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        line.addLineStation(new LineStation(1L, null, station1, line, 10, 10));
        line.addLineStation(new LineStation(2L, station1, station2, line, 10, 10));
        line.addLineStation(new LineStation(3L, station2, station3, line, 10, 10));
    }

    @Test
    void addLineStationAtTheFirstOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(stationRepository.findAllById(any())).thenReturn(Lists.list(station4));
        LineStationCreateRequest request = new LineStationCreateRequest(null, station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stationIds = line.getStationIds();
        assertThat(stationIds.get(0)).isEqualTo(station4);
        assertThat(stationIds.get(1)).isEqualTo(station1);
        assertThat(stationIds.get(2)).isEqualTo(station2);
        assertThat(stationIds.get(3)).isEqualTo(station3);
    }

    @Test
    void addLineStationBetweenTwo() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(stationRepository.findAllById(any())).thenReturn(Lists.list(station1, station4));

        LineStationCreateRequest request = new LineStationCreateRequest(station1.getId(), station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stationIds = line.getStationIds();
        assertThat(stationIds.get(0)).isEqualTo(station1);
        assertThat(stationIds.get(1)).isEqualTo(station4);
        assertThat(stationIds.get(2)).isEqualTo(station2);
        assertThat(stationIds.get(3)).isEqualTo(station3);
    }

    @Test
    void addLineStationAtTheEndOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        when(stationRepository.findAllById(any())).thenReturn(Lists.list(station3, station4));

        LineStationCreateRequest request = new LineStationCreateRequest(station3.getId(), station4.getId(), 10, 10);
        lineService.addLineStation(line.getId(), request);

        assertThat(line.getStations()).hasSize(4);

        List<Station> stationIds = line.getStationIds();
        assertThat(stationIds.get(0)).isEqualTo(station1);
        assertThat(stationIds.get(1)).isEqualTo(station2);
        assertThat(stationIds.get(2)).isEqualTo(station3);
        assertThat(stationIds.get(3)).isEqualTo(station4);
    }

    @Test
    void removeLineStationAtTheFirstOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 1L);

        assertThat(line.getStations()).hasSize(2);

        List<Station> stationIds = line.getStationIds();
        assertThat(stationIds.get(0)).isEqualTo(station2);
        assertThat(stationIds.get(1)).isEqualTo(station3);
    }

    @Test
    void removeLineStationBetweenTwo() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 2L);
    }

    @Test
    void removeLineStationAtTheEndOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 3L);

        assertThat(line.getStations()).hasSize(2);

        List<Station> stationIds = line.getStationIds();
        assertThat(stationIds.get(0)).isEqualTo(station1);
        assertThat(stationIds.get(1)).isEqualTo(station2);
    }
}
