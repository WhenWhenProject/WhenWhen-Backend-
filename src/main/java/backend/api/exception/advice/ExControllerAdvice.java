package backend.api.exception.advice;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.exception.PlanNotFoundException;
import backend.api.exception.UserNotFountException;
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
