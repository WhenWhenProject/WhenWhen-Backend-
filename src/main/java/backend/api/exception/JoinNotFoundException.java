package backend.api.exception;

public class JoinNotFoundException extends RuntimeException {

    public JoinNotFoundException() {
        super("참여 정보를 찾을 수 없습니다.");
    }

}
