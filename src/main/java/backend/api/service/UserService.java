package backend.api.service;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import backend.api.repository.user.UserRepository;
import backend.api.repository.user_refresh_token.UserRefreshTokenRepository;
import backend.api.service.dto.UserDto;
import backend.config.properties.AppProperties;
import backend.oauth.entity.RoleType;
import backend.oauth.entity.UserPrincipal;
import backend.token.AuthToken;
import backend.token.AuthTokenProvider;
import backend.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final AuthenticationManager authenticationManager;

    private final static String REFRESH_TOKEN = "refresh_token";

    @Transactional
    public UserDto signUp(String username, String password, String nickName, String email) {
        User user = User.builder()
                .username(username)
                .password(password)
                .nickName(nickName)
                .email(email)
                .roleType(RoleType.USER)
                .build();

        userRepository.save(user);

        return UserDto.of(user);
    }

    @Transactional
    public AuthToken login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        // username, password 에 해당하는 인증 객체를 가져옴. 내부에서 userDetailsService 호출
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // 시큐리티 컨텍스트에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 엑세스 토큰 생성
        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                username,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // 리프레시 토큰 db 저장 및 쿠키에 담기
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
        );

        if (userRefreshTokenRepository.findByUsername(username).isPresent()) {
            UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUsername(username).get();
            userRefreshToken.changeRefreshToken(refreshToken.getToken());
        } else {
            UserRefreshToken userRefreshToken = UserRefreshToken.builder()
                    .username(username)
                    .refreshToken(refreshToken.getToken())
                    .build();
            userRefreshTokenRepository.save(userRefreshToken);
        }

        int cookieMaxAge = (int) appProperties.getAuth().getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        // 엑세스 토큰 반환(response body 에 담아서 보내기)
        return accessToken;
    }

}
