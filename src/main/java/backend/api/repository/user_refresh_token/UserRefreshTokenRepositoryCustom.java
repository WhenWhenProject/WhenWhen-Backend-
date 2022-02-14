package backend.api.repository.user_refresh_token;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;

public interface UserRefreshTokenRepositoryCustom {

    UserRefreshToken findByUser(User user);
    UserRefreshToken findByUsername(String username);
    UserRefreshToken findByUserAndRefreshToken(User user, String refreshToken);
    UserRefreshToken findByRefreshToken(String refreshToken);

}
