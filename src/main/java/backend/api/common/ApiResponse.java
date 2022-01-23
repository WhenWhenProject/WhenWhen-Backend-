package backend.api.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    public final static Long SUCCESS = 200L;
    public final static Long NOT_FOUND = 400L;
    public final static Long SERVER_ERROR = 500L;

    private final Long code;
    private final T body;

}
