package backend.api.service;

import backend.api.controller.dto.request.ChangeUserRequest;
import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.request.TokenRefreshRequest;
import backend.api.entity.User;
import backend.api.exception.PasswordMisMatchException;
import backend.api.exception.UserNotFoundException;
import backend.api.repository.user.UserRepository;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.oauth.exception.TokenValidFailedException;
import backend.token.JwtToken;
import backend.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import static backend.util.HeaderConstant.HEADER_REFRESH_TOKEN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = passwordEncoder.encode(signUpRequest.getPassword());
        String nickName = signUpRequest.getNickName();
        String email = signUpRequest.getEmail();

        User user = User.builder()
                .username(username)
                .password(password)
                .nickName(nickName)
                .email(email)
                .roleType(RoleType.USER)
                .build();

        userRepository.save(user);
    }

    public JwtToken login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(password, user.getPassword())) throw new PasswordMisMatchException();

        JwtToken jwtToken = jwtTokenProvider.createJwtToken(username, user.getRoleType().getCode());

        return jwtToken;
    }

    public JwtToken refresh(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();

        if (refreshToken != null && jwtTokenProvider.verifyToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);

            User user = userRepository.findByUsername(username)
                    .orElseThrow(UserNotFoundException::new);

            JwtToken jwtToken = jwtTokenProvider.createJwtToken(username, user.getRoleType().getCode());

            return jwtToken;
        }

        throw new TokenValidFailedException();
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return UserDto.of(user);
    }

    @Transactional
    public void changeUserInfo(String username, ChangeUserRequest changeUserRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        changeUserRequest.apply(user);
    }

}
