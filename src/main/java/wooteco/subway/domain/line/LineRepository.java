package wooteco.subway.domain.line;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LineRepository extends JpaRepository<Line, Long> {
    @Query("SELECT DISTINCT l from Line l JOIN FETCH l.stations.stations ls LEFT JOIN FETCH ls.preStation ps JOIN FETCH ls.station s")
    List<Line> findAllWithStations();
}
