package backend.api.exception.advice;

import backend.api.controller.dto.common.ApiResponse;
import backend.api.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "backend")
public class ExControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<String> UserNotFoundExHandler(UserNotFoundException e) {
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler(PlanNotFoundException.class)
    public ApiResponse<String> planNotFoundExHandler(PlanNotFoundException e) {
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler(JoinNotFoundException.class)
    public ApiResponse<String> joinNotFoundExHandler(JoinNotFoundException e) {
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler(PasswordMisMatchException.class)
    public ApiResponse<String> passwordMisMatchExHandler(PasswordMisMatchException e) {
        return new ApiResponse<>(e.getMessage());
    }

    @ExceptionHandler(JoinAlreadyExistException.class)
    public ApiResponse<String> joinAlreadyExistExHandler(JoinAlreadyExistException e) {
        return new ApiResponse<>(e.getMessage());
    }

}
