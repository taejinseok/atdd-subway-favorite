package wooteco.subway.service.line;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import wooteco.subway.service.line.dto.LineDetailResponse;

@ExtendWith(MockitoExtension.class)
public class LineStationServiceTest {
    private static final String STATION_NAME1 = "강남역";
    private static final String STATION_NAME2 = "역삼역";
    private static final String STATION_NAME3 = "선릉역";
    private static final String STATION_NAME4 = "삼성역";
    private static final String STATION_NAME5 = "성신여대입구역";
    private static final String STATION_NAME6 = "한성대입구역";

    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationRepository stationRepository;

    private LineStationService lineStationService;

    private Line line;
    private Station station1;
    private Station station2;
    private Station station3;
    private Station station4;
    private Station station5;
    private Station station6;

    @BeforeEach
    void setUp() {
        lineStationService = new LineStationService(lineRepository);

        station1 = new Station(1L, STATION_NAME1);
        station2 = new Station(2L, STATION_NAME2);
        station3 = new Station(3L, STATION_NAME3);
        station4 = new Station(4L, STATION_NAME4);
        station5 = new Station(5L, STATION_NAME5);
        station6 = new Station(6L, STATION_NAME6);

        line = new Line(1L, "2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        line.addLineStation(new LineStation(1L, null, station1, line, 10, 10));
        line.addLineStation(new LineStation(2L, station1, station2, line, 10, 10));
        line.addLineStation(new LineStation(3L, station2, station3, line, 10, 10));
    }

    @Test
    void findLineWithStationsById() {
        when(lineRepository.findById(anyLong())).thenReturn(Optional.of(line));

        LineDetailResponse lineDetailResponse = lineStationService.findLineWithStationsById(1L);

        assertThat(lineDetailResponse.getStations()).hasSize(3);
    }

    @Test
    void wholeLines() {
        Line newLine = new Line(2L, "신분당선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5);
        newLine.addLineStation(new LineStation(4L, null, station4, newLine, 10, 10));
        newLine.addLineStation(new LineStation(5L, station4, station5, newLine, 10, 10));
        newLine.addLineStation(new LineStation(6L, station5, station6, newLine, 10, 10));

        when(lineRepository.findAll()).thenReturn(Arrays.asList(this.line, newLine));

        List<LineDetailResponse> lineDetails = lineStationService.findLinesWithStations().getLineDetailResponse();

        assertThat(lineDetails).isNotNull();
        assertThat(lineDetails.get(0).getStations().size()).isEqualTo(3);
        assertThat(lineDetails.get(1).getStations().size()).isEqualTo(3);
    }
}
