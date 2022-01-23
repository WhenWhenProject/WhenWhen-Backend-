package backend.exception;

import backend.api.common.ApiResponse;
import backend.api.error.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "backend")
public class ExControllerAdvice {

    @ExceptionHandler(UserNotFountException.class)
    public ApiResponse<ErrorResponse> UserNotFoundExHandler(UserNotFountException e) {
        return new ApiResponse<>(ApiResponse.NOT_FOUND, new ErrorResponse(e.getMessage()));
    }

}
