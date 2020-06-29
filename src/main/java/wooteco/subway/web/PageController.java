package wooteco.subway.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import wooteco.subway.service.line.LineService;
import wooteco.subway.service.station.StationService;

@Controller
public class PageController {
    private final LineService lineService;
    private final StationService stationService;

    public PageController(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping(value = "/admin", produces = MediaType.TEXT_HTML_VALUE)
    public String adminIndexPage() {
        return "admin/index";
    }

    @GetMapping(value = "/admin/stations", produces = MediaType.TEXT_HTML_VALUE)
    public String stationPage(Model model) {
        model.addAttribute("stations", stationService.findStations());
        return "admin/admin-station";
    }

    @GetMapping(value = "/admin/lines", produces = MediaType.TEXT_HTML_VALUE)
    public String linePage(Model model) {
        model.addAttribute("lines", lineService.findLines());
        return "admin/admin-line";
    }

    @GetMapping(value = "/admin/edges", produces = MediaType.TEXT_HTML_VALUE)
    public String edgePage() {
        return "admin/admin-edge";
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String indexPage() {
        return "service/index";
    }

    @GetMapping(value = "/map", produces = MediaType.TEXT_HTML_VALUE)
    public String mapPage() {
        return "service/map";
    }

    @GetMapping(value = "/search", produces = MediaType.TEXT_HTML_VALUE)
    public String searchPage() {
        return "service/search";
    }

    @GetMapping(value = "/join", produces = MediaType.TEXT_HTML_VALUE)
    public String joinPage() {
        return "service/join";
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public String loginPage() {
        return "service/login";
    }

    @GetMapping(value = "/favorites", produces = MediaType.TEXT_HTML_VALUE)
    public String favoritesPage() {
        return "service/favorite";
    }

    @GetMapping(value = "/mypage", produces = MediaType.TEXT_HTML_VALUE)
    public String myInfoPage() {
        return "service/mypage";
    }

    @GetMapping(value = "/mypage-edit", produces = MediaType.TEXT_HTML_VALUE)
    public String myInfoEditPage() {
        return "service/mypage-edit";
    }
}
