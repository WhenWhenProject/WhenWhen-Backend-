package backend.api.service.dto;

import backend.api.entity.User;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;
    private String username;
    private String nickName;
    private String email;
    private String profileImageUrl;
    private ProviderType providerType;
    private RoleType roleType;

    @Builder
    private UserDto(Long id, String username, String nickName, String email, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.id = id;
        this.username = username;
        this.nickName = nickName;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .profileImageUrl(user.getProfileImageUrl())
                .providerType(user.getProviderType())
                .roleType(user.getRoleType())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickName(nickName)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .providerType(providerType)
                .roleType(roleType)
                .build();
    }

}
