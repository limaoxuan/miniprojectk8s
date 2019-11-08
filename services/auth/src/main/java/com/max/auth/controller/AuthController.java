package com.max.auth.controller;

import com.max.auth.VO.AuthVO;
import com.max.auth.VO.ResultVO;
import com.max.auth.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.util.Date;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long ttl = 3600000;//3600s

    //    Keys.
    @PostMapping("/gen_token")
    public String genToken(String id, String email, String role) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(email).setIssuedAt(now).signWith(key).claim("role", role);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    @PostMapping("/valid_token")
    public ResultVO<Boolean> validToken(@RequestBody TokenDTO tokenDTO) {
        System.out.println(tokenDTO.getToken());
        Claims body = null;
        try {

            body = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenDTO.getToken()).getBody();

        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the sub field
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
        } finally {
            ResultVO<Boolean> resultVO = new ResultVO<>();
            resultVO.setCode(0);
            resultVO.setMsg("invalid token");
            resultVO.setData(false);
            if (body != null) {
                String subject = body.getSubject();
                String id = body.getId();
                if (id.equals("1") && subject.equals("hello")) {
                    resultVO.setMsg("success");
                    resultVO.setData(true);
                    return resultVO;
                }
            }
            return resultVO;

        }


    }


}
