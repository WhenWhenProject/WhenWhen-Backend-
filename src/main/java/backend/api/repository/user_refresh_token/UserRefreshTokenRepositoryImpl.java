package backend.api.repository.user_refresh_token;

import backend.api.entity.QUser;
import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import static backend.api.entity.QUser.user;
import static backend.api.entity.QUserRefreshToken.userRefreshToken;

public class UserRefreshTokenRepositoryImpl extends MyQuerydslRepositorySupport implements UserRefreshTokenRepositoryCustom {

    public UserRefreshTokenRepositoryImpl() {
        super(UserRefreshToken.class);
    }

    @Override
    public UserRefreshToken findByUser(User user) {
        return getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(userRefreshToken.user.eq(user))
                .fetchOne();
    }

    @Override
    public UserRefreshToken findByUsername(String username) {
        return getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .join(userRefreshToken.user, user).fetchJoin()
                .where(user.username.eq(username))
                .fetchOne();
    }

    @Override
    public UserRefreshToken findByUserAndRefreshToken(User user, String refreshToken) {
        return getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(
                        userRefreshToken.refreshToken.eq(refreshToken)
                                        .and(userRefreshToken.user.eq(user))
                )
                .fetchOne();
    }

    @Override
    public UserRefreshToken findByRefreshToken(String refreshToken) {
        return getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(userRefreshToken.refreshToken.eq(refreshToken))
                .fetchOne();
    }

}