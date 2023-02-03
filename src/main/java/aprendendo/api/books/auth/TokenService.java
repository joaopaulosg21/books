package aprendendo.api.books.auth;

import aprendendo.api.books.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.exp}")
    private String exp;

    public String generate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        long expiration = new Date().getTime() + Long.parseLong(exp);
        Map<String,Object> claims = new HashMap<>();
        claims.put("user",user);
        return Jwts
                .builder()
                .setExpiration(new Date(expiration))
                .setSubject(user.getId().toString())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isValid(String token) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception exc) {
            return false;
        }
    }

    public User getUserFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(claims.get("user"),User.class);
        return user;
    }
}
