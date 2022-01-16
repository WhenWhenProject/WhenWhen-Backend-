package backend.repository.user;

import backend.domain.Plan;
import backend.domain.User;
import backend.repository.MyQuerydslRepositorySupport;

public class UserRepositoryImpl extends MyQuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }

}
