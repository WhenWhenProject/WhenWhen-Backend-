package backend.token;

import io.jsonwebtoken.*;
import lombok.Getter;

import java.security.Key;
import java.util.Date;

@Getter
public class AuthToken {

    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    protected AuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    protected AuthToken(String username, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(username, expiry);
    }

    protected AuthToken(String username, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(username, role, expiry);
    }

    private String createAuthToken(String username, Date expiry) {
        System.out.println("AuthToken.createAuthToken()");
        return Jwts.builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String username, String role, Date expiry) {
        System.out.println("AuthToken.createAuthToken()");
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        System.out.println("AuthToken.validate()");
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        System.out.println("AuthToken.getTokenClaims()");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT token compact of handler are invalid.");
        }

        return null;
    }

    public Claims getExpiredTokenClaims() {
        System.out.println("AuthToken.getExpiredTokenClaims()");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token.");
            return e.getClaims();
        }

        return null;
    }

}