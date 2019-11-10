package com.max.auth.controller;

import com.max.auth.VO.AuthVO;
import com.max.auth.VO.ResultVO;
import com.max.auth.dto.AccountDTO;
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
    public String genToken(@RequestBody AccountDTO accountDTO) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        System.out.println(accountDTO.getEmail());
        System.out.println(accountDTO.getRole());
        JwtBuilder builder = Jwts.builder().setId(accountDTO.getId()).setSubject(accountDTO.getEmail()).setIssuedAt(now).signWith(key).claim("role", accountDTO.getRole());
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    @PostMapping("/valid_token")
    public ResultVO<String> validToken(@RequestBody TokenDTO tokenDTO) {
        System.out.println("valid_token");
        System.out.println(tokenDTO.getToken());
        Jws<Claims> jws;
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(1);
        resultVO.setMsg("invalid token");
        resultVO.setData("{}");
        try {
            jws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(tokenDTO.getToken());
            System.out.println(jws.getBody().getSubject());
            resultVO.setCode(0);
            resultVO.setMsg("valid token");
            resultVO.setData(jws.getBody().getSubject());
        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the sub field
            System.out.println("mce");
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
            System.out.println("ice");
        } catch (Exception e) {

        } finally {

        }
        return resultVO;
    }


}
