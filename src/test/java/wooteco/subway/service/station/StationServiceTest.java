package wooteco.subway.service.station;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineRepository;
import wooteco.subway.domain.line.LineStation;
import wooteco.subway.domain.station.Station;
import wooteco.subway.domain.station.StationRepository;

@Sql("/truncate.sql")
@SpringBootTest
@Transactional
public class StationServiceTest {
    @Autowired
    private StationService stationService;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;

    @Test
    public void removeStation() {
        Station station1 = stationRepository.save(new Station("강남역"));
        Station station2 = stationRepository.save(new Station("역삼역"));
        Line line = lineRepository.save(new Line("2호선", LocalTime.of(5, 30), LocalTime.of(22, 30), 10));

        line.addLineStation(new LineStation(null, station1, line, 10, 10));
        line.addLineStation(new LineStation(station1, station2, line, 10, 10));
        lineRepository.save(line);

        stationService.deleteStationById(station1.getId());

        Optional<Station> resultStation = stationRepository.findById(station1.getId());
        assertThat(resultStation).isEmpty();

        Line resultLine = lineRepository.findById(line.getId()).orElseThrow(RuntimeException::new);
        assertThat(resultLine.getStations()).hasSize(1);
    }
}

