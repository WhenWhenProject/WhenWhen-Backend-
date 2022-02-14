package backend.token;

import backend.oauth.entity.PrincipalDetails;
import backend.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    private final Key key;

    private static final String AUTHORITIES_CLAIM_NAME = "role";

    public JwtTokenProvider(@Value("${jwt.secret}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public JwtToken createJwtToken(String username, Date expireDate) {
        return new JwtToken(username, expireDate, key);
    }

    public JwtToken createJwtToken(String username, String role, Date expireDate) {
        return new JwtToken(username, role, expireDate, key);
    }

    public JwtToken convertToJwtToken(String token) {
        return new JwtToken(token, key);
    }

    public Authentication getAuthentication(JwtToken jwtToken) {
        if(jwtToken.validate()) {
            Claims claims = jwtToken.getTokenClaims();

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(claims.get(AUTHORITIES_CLAIM_NAME).toString()));

            PrincipalDetails principalDetails = PrincipalDetails.builder()
                    .username(claims.getSubject())
                    .providerType(null)
                    .roleType(null)
                    .authorities(authorities)
                    .attributes(null)
                    .build();

            return new UsernamePasswordAuthenticationToken(principalDetails, jwtToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

}
