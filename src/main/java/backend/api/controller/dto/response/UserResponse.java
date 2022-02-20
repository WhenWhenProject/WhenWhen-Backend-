package backend.api.controller.dto.response;

import backend.api.service.dto.UserDto;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private String nickName;
    private String email;
    private String profileImageUrl;
    private ProviderType providerType;
    private RoleType roleType;

    @Builder
    private UserResponse(String nickName, String email, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.nickName = nickName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public static UserResponse of(UserDto userDto) {
        return UserResponse.builder()
                .nickName(userDto.getNickName())
                .email(userDto.getEmail())
                .profileImageUrl(userDto.getProfileImageUrl())
                .providerType(userDto.getProviderType())
                .roleType(userDto.getRoleType())
                .build();
    }

}
