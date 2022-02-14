package backend.api.exception;

public class UserNotFountException extends RuntimeException {

    public UserNotFountException() {
        super("사용자를 찾을 수 없습니다.");
    }

    public UserNotFountException(String message) {
        super(message);
    }

}