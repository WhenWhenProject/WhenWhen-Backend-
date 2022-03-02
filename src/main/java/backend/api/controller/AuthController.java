package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.LoginRequest;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<String> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);

        return ApiResponse.success("signUp", "success");
    }

    @PostMapping("/login")
    public ApiResponse<String> login(HttpServletResponse response, @RequestBody LoginRequest loginRequest) {
        userService.login(response, loginRequest);

        return ApiResponse.success("login", "success");
    }

    @GetMapping("/refresh")
    public ApiResponse<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        userService.refresh(request, response);

        return ApiResponse.success("refresh", "success");
    }

}