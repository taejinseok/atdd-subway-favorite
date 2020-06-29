package wooteco.subway.exception;

public class StationNotFoundException extends EntityNotFoundException {
    private static final String EXCEPTION_MESSAGE = "해당 역이 존재하지 않습니다.";

    public StationNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
