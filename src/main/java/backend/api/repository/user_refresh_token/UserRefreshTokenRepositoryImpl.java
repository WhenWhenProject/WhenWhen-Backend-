package backend.api.repository.user_refresh_token;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import java.util.Optional;

import static backend.api.entity.QUserRefreshToken.userRefreshToken;

public class UserRefreshTokenRepositoryImpl extends MyQuerydslRepositorySupport implements UserRefreshTokenRepositoryCustom {

    public UserRefreshTokenRepositoryImpl() {
        super(UserRefreshToken.class);
    }

    @Override
    public Optional<UserRefreshToken> findByUser(User user) {
        UserRefreshToken result = getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(userRefreshToken.username.eq(user.getUsername()))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserRefreshToken> findByUserAndRefreshToken(User user, String refreshToken) {
        UserRefreshToken result = getQueryFactory()
                .select(userRefreshToken)
                .from(userRefreshToken)
                .where(
                        userRefreshToken.refreshToken.eq(refreshToken).and(
                                userRefreshToken.username.eq(user.getUsername()))
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
