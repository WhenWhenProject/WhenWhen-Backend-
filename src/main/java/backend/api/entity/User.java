package backend.api.entity;

import backend.api.entity.common.BaseTimeEntity;
import backend.oauth.entity.ProviderType;
import backend.oauth.entity.RoleType;
import lombok.*;

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

    @Column(length = 64, unique = true)
    private String username;

    @Column(length = 128)
    private String password;

    @Column(length = 100)
    private String nickName;

    @Column(length = 512)
    private String email;

    @Column(length = 512)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProviderType providerType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    private User(
            @NotNull @Size(max = 64) String username,
            @Size(max = 128) String password,
            @NotNull @Size(max = 100) String nickName,
            @Size(max = 512) String email,
            @Size(max = 512) String profileImageUrl,
            ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.username = username;
        this.password = password != null ? password : "NO_PASS";
        this.nickName = nickName;
        this.email = email != null ? email : "NO_EMAIL";
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

}
