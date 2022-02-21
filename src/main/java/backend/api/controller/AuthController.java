package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.response.UserResponse;
import backend.api.service.UserService;
import backend.api.service.dto.UserDto;
import backend.token.AuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
        AuthToken accessToken = userService.login(request, response, loginRequest.getUsername(), passwordEncoder.encode(loginRequest.getPassword()));

        return ApiResponse.success("accessToken", accessToken.getToken());
    }

}