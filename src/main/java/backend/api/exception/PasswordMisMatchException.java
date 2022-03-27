package backend.api.exception;

public class PasswordMisMatchException extends RuntimeException {

    public PasswordMisMatchException() {
        super("비밀번호가 잘못되었습니다.");
    }

}
