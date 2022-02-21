package backend.api.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class LoginRequest {

    @NotNull @Size(min = 5, max = 64)
    private String username;

    @NotNull @Size(min = 5, max = 50)
    private String password;

}
