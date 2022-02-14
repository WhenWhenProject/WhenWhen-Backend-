package backend.api.repository.user_refresh_token;

import backend.api.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long>, UserRefreshTokenRepositoryCustom {

}

