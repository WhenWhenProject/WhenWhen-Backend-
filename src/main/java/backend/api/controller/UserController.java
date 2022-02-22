package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.controller.dto.request.ChangeUserRequest;
import backend.api.controller.dto.response.UserResponse;
import backend.api.service.UserService;
import backend.api.service.dto.UserDto;
import backend.argumentresolver.CurrentUser;
import backend.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> find(@CurrentUser UserPrincipal userPrincipal) {
        UserDto userDto = userService.findByUsername(userPrincipal.getUsername());

        return ApiResponse.success("user", UserResponse.of(userDto));
    }

    @PatchMapping("/my-info")
    public ApiResponse<UserResponse> update(@CurrentUser UserPrincipal userPrincipal, @RequestBody ChangeUserRequest changeUserRequest) {
        UserDto userDto = userService.changeUserInfo(userPrincipal.getUsername(), changeUserRequest);

        return ApiResponse.success("user", UserResponse.of(userDto));
    }

}







