package backend.api.service.dto;

import backend.api.entity.UserRefreshToken;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRefreshTokenDto {

    private Long id;
    private String username;
    private String refreshToken;

    @Builder
    private UserRefreshTokenDto(Long id, String username, String refreshToken) {
        this.id = id;
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public static UserRefreshTokenDto of(UserRefreshToken userRefreshToken) {
        return UserRefreshTokenDto.builder()
                .id(userRefreshToken.getId())
                .username(userRefreshToken.getUsername())
                .refreshToken(userRefreshToken.getRefreshToken())
                .build();
    }

    public UserRefreshToken toEntity() {
        return UserRefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build();
    }

}
