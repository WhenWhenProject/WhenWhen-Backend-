package backend.api.repository.user;

import backend.api.entity.User;
import backend.api.repository.util.MyQuerydslRepositorySupport;

public class UserRepositoryImpl extends MyQuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }


}
