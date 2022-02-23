package backend.token;

import backend.oauth.entity.UserPrincipal;
import backend.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String username, Date expiry) {
        System.out.println("AuthTokenProvider.createAuthToken()");
        return new AuthToken(username, expiry, key);
    }

    public AuthToken createAuthToken(String username, String role, Date expiry) {
        System.out.println("AuthTokenProvider.createAuthToken()");
        return new AuthToken(username, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        System.out.println("AuthTokenProvider.convertAuthToken()");
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {
        System.out.println("AuthTokenProvider.getAuthentication()");
        if(authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities = Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            System.out.printf("AuthTokenProvider.getAuthentication() : claims subject := [%s]", claims.getSubject());

            UserPrincipal userPrincipal = UserPrincipal.builder()
                    .username(claims.getSubject())
                    .password("")
                    .authorities(authorities)
                    .build();

            return new UsernamePasswordAuthenticationToken(userPrincipal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

}
