package backend.api.entity;

import backend.api.entity.common.BaseTimeEntity;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(max = 64)
    @Column(length = 64, unique = true)
    private String username;

    @NotNull @Size(max = 100)
    @Column(length = 100)
    private String nickName;

    @NotNull @Size(max = 512)
    @Column(length = 512, unique = true)
    private String email;

    @NotNull @Size(max = 512)
    @Column(length = 512)
    private String profileImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProviderType providerType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @Builder
    public User(
            @NotNull @Size(max = 64) String username,
            @NotNull @Size(max = 100) String nickName,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.username = username;
        this.nickName = StringUtils.hasText(nickName) ? nickName : "닉네임";
        this.email = StringUtils.hasText(email) ? email : "no_email";
        this.profileImageUrl = StringUtils.hasText(profileImageUrl) ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

}
