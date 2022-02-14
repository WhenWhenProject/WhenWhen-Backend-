package backend.api.exception;

import backend.api.controller.dto.common.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "backend")
public class ExControllerAdvice {

    @ExceptionHandler(UserNotFountException.class)
    public ApiResponse<String> UserNotFoundExHandler(UserNotFountException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ApiResponse<String> planNotFoundExHandler(PlanNotFoundException e) {
        return ApiResponse.fail(e.getMessage());
    }

}
