package backend.api.service;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import backend.api.exception.UserNotFountException;
import backend.api.repository.user.UserRepository;
import backend.api.repository.user_refresh_token.UserRefreshTokenRepository;
import backend.api.service.dto.UserRefreshTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserRefreshTokenService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;

    public UserRefreshTokenDto findByUsernameAndRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFountException::new);

        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserAndRefreshToken(user, refreshToken);

        return userRefreshToken == null ? null : UserRefreshTokenDto.of(userRefreshToken);
    }

    @Transactional
    public void changeRefreshToken(String originalRefreshToken, String newRefreshToken) {
        UserRefreshToken findUserRefreshToken = userRefreshTokenRepository.findByRefreshToken(originalRefreshToken);
        findUserRefreshToken.changeRefreshToken(newRefreshToken);
    }

}
