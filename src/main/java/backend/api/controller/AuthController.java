package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.response.UserResponse;
import backend.api.service.UserService;
import backend.api.service.dto.UserDto;
import backend.oauth.entity.RoleType;
import backend.token.AuthToken;
import backend.token.AuthTokenProvider;
import backend.util.CookieUtil;
import backend.util.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenProvider tokenProvider;

    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/sign-up")
    public ApiResponse<UserResponse> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = passwordEncoder.encode(signUpRequest.getPassword());
        String nickName = signUpRequest.getNickName();
        String email = signUpRequest.getEmail();

        UserDto userDto = userService.signUp(username, password, nickName, email);

        return ApiResponse.success("user", UserResponse.of(userDto));
    }

    @PostMapping("/login")
    public ApiResponse<String> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginRequest loginRequest) {
        AuthToken accessToken = userService.login(request, response, loginRequest.getUsername(), loginRequest.getPassword());
        return ApiResponse.success("accessToken", accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        AuthToken accessToken = tokenProvider.convertAuthToken(HeaderUtil.getAuthToken(request));

        if (!accessToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = accessToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String username = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        AuthToken refreshToken = tokenProvider.convertAuthToken(
                CookieUtil.getCookie(request, REFRESH_TOKEN)
                        .map(Cookie::getValue)
                        .orElse((null))
        );

        if (!refreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        AuthToken newAccessToken = userService.updateRefreshToken(request, response, username, roleType, refreshToken);

        if (newAccessToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        return ApiResponse.success("accessToken", newAccessToken.getToken());
    }

}