package backend.api.service.dto;

import backend.api.entity.User;
import backend.api.entity.UserRefreshToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRefreshTokenDto {

    private Long id;
    private User user;
    private String refreshToken;

    @Builder
    private UserRefreshTokenDto(Long id, User user, String refreshToken) {
        this.id = id;
        this.user = user;
        this.refreshToken = refreshToken;
    }

    public static UserRefreshTokenDto of(UserRefreshToken userRefreshToken) {
        return UserRefreshTokenDto.builder()
                .id(userRefreshToken.getId())
                .user(userRefreshToken.getUser())
                .refreshToken(userRefreshToken.getRefreshToken())
                .build();
    }

}
