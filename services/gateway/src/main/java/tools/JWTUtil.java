package tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    private String key = "ea_max" ;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    /**
     *
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String role) {
        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, key).claim("role", role);

        return builder.compact();
    }

    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
