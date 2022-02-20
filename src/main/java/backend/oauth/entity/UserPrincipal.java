package backend.oauth.entity;

import backend.api.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User, OidcUser {

    private final String username;
    private final String password;
    private final ProviderType providerType;
    private final RoleType roleType;

    // 부가적인 정보들
    private final Collection<GrantedAuthority> authorities; // roleType 을 Authority 타입으로 바꾼 것. ( 우리 서버에서는 role 이 계정 당 1 개이므로 컬렉션의 사이즈는 1 )
    private final Map<String, Object> attributes; // OAuth2User 에서 필요. 여기에는 OAuth 서버에서 받은 속성들이 들어있다.

    @Builder
    private UserPrincipal(String username, String password, ProviderType providerType, RoleType roleType, Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.username = username;
        this.password = password;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Builder
    private UserPrincipal(String username, String password, ProviderType providerType, RoleType roleType, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
        this.attributes = null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    } // oauth2 에서 필요

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.getProviderType(),
                RoleType.USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.getProviderType(),
                RoleType.USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())),
                attributes
        );
    }

}
