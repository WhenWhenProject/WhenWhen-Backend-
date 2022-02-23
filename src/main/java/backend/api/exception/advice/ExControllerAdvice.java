package backend.api.exception.advice;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.exception.JoinNotFoundException;
import backend.api.exception.PlanNotFoundException;
import backend.api.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "backend")
public class ExControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<String> UserNotFoundExHandler(UserNotFoundException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ApiResponse<String> planNotFoundExHandler(PlanNotFoundException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ExceptionHandler(JoinNotFoundException.class)
    public ApiResponse<String> joinNotFoundExHandler(JoinNotFoundException e) {
        return ApiResponse.fail(e.getMessage());
    }

}
