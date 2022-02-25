package backend.api.exception;

public class NoAuthorizationException  extends RuntimeException {

    public NoAuthorizationException() {
        super("해당 명령의 실행 권한이 없습니다.");
    }

    public NoAuthorizationException(String message) {
        super(message);
    }

}
