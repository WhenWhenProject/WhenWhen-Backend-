package backend.api.repository.user_refresh_token;

import backend.api.entity.QUserRefreshToken;
import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import java.util.Optional;

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
                .where(userRefreshToken.username.eq(user.getUsername()))
                .fetchOne();
    }

    @Override
    public Optional<UserRefreshToken> findByUsername(String username) {
        UserRefreshToken userRefreshToken = getQueryFactory()
                .select(QUserRefreshToken.userRefreshToken)
                .from(QUserRefreshToken.userRefreshToken)
                .where(user.username.eq(username))
                .fetchOne();

        return Optional.ofNullable(userRefreshToken);
    }

    @Override
    public UserRefreshToken findByUserAndRefreshToken(User user, String refreshToken) {
        return getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(
                        userRefreshToken.refreshToken.eq(refreshToken)
                                        .and(userRefreshToken.username.eq(user.getUsername()))
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
