package deisinger.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${jwt.base64-secret}")
    private String base64secret;

    @Value("${jwt.token-validity-seconds}")
    private Integer validityInSeconds;

    public String generateToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(base64secret.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder()
                .setIssuer("Staycation")
                .setSubject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities",
                        AuthorityUtils.authorityListToSet(authentication.getAuthorities())
                                .stream()
                                .reduce("", (partialString, element) -> partialString + ", " + element))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + validityInSeconds * 1000))
                .signWith(key)
                .compact();

        return jwt;
    }
}
