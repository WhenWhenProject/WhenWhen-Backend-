package backend.api.exception;

public class JoinAlreadyExistException extends RuntimeException {

    public JoinAlreadyExistException() {
        super("이미 일정에 참여하였습니다.");
    }

}
