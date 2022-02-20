package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.SignUpRequest;
import backend.api.controller.dto.response.UserResponse;
import backend.api.service.UserService;
import backend.api.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<UserResponse> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        String nickName = signUpRequest.getNickName();
        String email = signUpRequest.getEmail();

        UserDto userDto = userService.signUp(username, password, nickName, email);

        return ApiResponse.success("user", UserResponse.of(userDto));
    }

}