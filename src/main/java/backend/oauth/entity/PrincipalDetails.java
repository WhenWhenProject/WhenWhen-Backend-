package backend.oauth.entity;

import backend.api.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements OAuth2User, OidcUser {

    private final String username;
    private final ProviderType providerType;
    private final RoleType roleType;

    // 부가적인 정보들
    private final Collection<GrantedAuthority> authorities; // roleType 을 Authority 타입으로 바꾼 것. ( 우리 서버에서는 role 이 계정 당 1 개이므로 컬렉션의 사이즈는 1 )
    private final Map<String, Object> attributes; // OAuth2User 에서 필요. 여기에는 OAuth 서버에서 받은 속성들이 들어있다.

    @Override
    public Map<String, Object> getAttributes() { return attributes; } // OAuth2User 에서 필요

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Builder
    protected PrincipalDetails(String username, ProviderType providerType, RoleType roleType, Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.username = username;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static PrincipalDetails create(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleType().getCode()));

        return PrincipalDetails.builder()
                .username(user.getUsername())
                .providerType(user.getProviderType())
                .roleType(user.getRoleType())
                .authorities(authorities)
                .attributes(null)
                .build();
    }

    public static PrincipalDetails create(User user, Map<String, Object> attributes) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleType().getCode()));

        return PrincipalDetails.builder()
                .username(user.getUsername())
                .providerType(user.getProviderType())
                .roleType(user.getRoleType())
                .authorities(authorities)
                .attributes(attributes)
                .build();
    }

}
