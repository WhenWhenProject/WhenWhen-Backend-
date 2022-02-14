package backend.api.controller.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponseHeader {

    private int code;
    private String message;

}
