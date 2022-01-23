package backend.dto;

import backend.domain.User;
import backend.domain.enumeration.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserDto {

    private Long userId;
    private Role role;

    public static UserDto of(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

}
