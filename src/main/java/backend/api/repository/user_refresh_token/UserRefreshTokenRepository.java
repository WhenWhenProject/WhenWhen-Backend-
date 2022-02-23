package backend.api.repository.user_refresh_token;

import backend.api.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long>, UserRefreshTokenRepositoryCustom {

    Optional<UserRefreshToken> findByUsernameAndRefreshToken(String username, String refreshTokenToken);
    Optional<UserRefreshToken> findByUsername(String username);
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);


}

