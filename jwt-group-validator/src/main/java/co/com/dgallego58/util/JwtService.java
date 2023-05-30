package co.com.dgallego58.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtService<T> {

    private static final int TWO_PARTS = 2;
    private String token;
    private String type;
    private String decomposed;

    private JwtService() {
        //private
    }


    public JwtService(String bearerAuth) {
        var parts = bearerAuth.split(" ");
        this.token = parts[1];
        this.type = parts[0];
        this.decomposed = decompose();
    }

    public String getType() {
        return type;
    }

    public String newJws(Map<String, Object> payload) {
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Jwts.builder().signWith(key).setClaims(payload).compact();
    }

    public String decompose() {
        return Arrays.stream(token.split("\\."))
                     .limit(TWO_PARTS)
                     .collect(Collectors.joining(".", "", "."));
    }

    public Map<String, Object> bodyAsMap() {
        return Jwts.parserBuilder().build().parseClaimsJwt(decomposed).getBody();
    }

    public T asType(Class<T> clazz) {
        var claims = Jwts.parserBuilder().build().parseClaimsJwt(decomposed).getBody();
        return new ObjectMapper().convertValue(claims, clazz);
    }
}
