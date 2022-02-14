package backend.api.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "_user_refresh_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull @Size(max = 256)
    @Column(length = 256)
    private String refreshToken;

    @Builder
    public UserRefreshToken(
            User user,
            @NotNull @Size(max = 256) String refreshToken
    ) {
        this.user = user;
        this.refreshToken = refreshToken;
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
