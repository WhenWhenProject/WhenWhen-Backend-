package backend.api.repository.user_refresh_token;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;

import java.util.Optional;

public interface UserRefreshTokenRepositoryCustom {

    Optional<UserRefreshToken> findByUser(User user);
    Optional<UserRefreshToken> findByUserAndRefreshToken(User user, String refreshToken);

}
