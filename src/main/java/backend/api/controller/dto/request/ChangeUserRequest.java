package backend.api.controller.dto.request;

import backend.api.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
public class ChangeUserRequest {

    @NotNull @Size(min = 1, max = 50)
    private String nickName;

    @Size(max = 512)
    private String email;

    public void apply(User user) {
        user.changeEmail(email);
        user.changeNickName(nickName);
    }

}
