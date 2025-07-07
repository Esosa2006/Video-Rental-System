package VRS.Video.Rental.System.security;

import VRS.Video.Rental.System.utils.SecretKeyGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {
    private final String secretKey = SecretKeyGenerator.generateSecretKey();

    public JWTService() throws NoSuchAlgorithmException {
    }

    public String generateToken(String username){

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 36000))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    private Date getExpiration(String token){
        return getAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username =  getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
