package backend.api.exception;

public class PlanNotFoundException extends RuntimeException {

    public PlanNotFoundException() {
        super("일정을 찾을 수 없습니다.");
    }

    public PlanNotFoundException(String message) {
        super(message);
    }

}
