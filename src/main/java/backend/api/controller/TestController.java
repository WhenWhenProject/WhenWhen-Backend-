package backend.api.controller;

import backend.api.controller.dto.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public ApiResponse<String> test() {
        return new ApiResponse<>("hello");
    }

}
