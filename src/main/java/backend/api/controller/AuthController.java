package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.request.TokenRefreshRequest;
import backend.api.service.UserService;
import backend.token.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<String> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);

        return new ApiResponse<>("success");
    }

    @PostMapping("/login")
    public ApiResponse<JwtToken> login(@RequestBody LoginRequest loginRequest) {
        JwtToken jwtToken = userService.login(loginRequest);

        return new ApiResponse<>(jwtToken);
    }

    @GetMapping("/refresh")
    public ApiResponse<JwtToken> refresh(TokenRefreshRequest tokenRefreshRequest) {
        JwtToken jwtToken = userService.refresh(tokenRefreshRequest);

        return new ApiResponse<>(jwtToken);
    }

}