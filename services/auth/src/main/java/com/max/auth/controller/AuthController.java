package com.max.auth.controller;

import com.max.auth.VO.AuthVO;
import com.max.auth.VO.ResultVO;
import com.max.auth.dto.AccountDTO;
import com.max.auth.dto.TokenDTO;
import com.max.auth.service.AuthService;
import com.max.auth.tools.JWTUtil;
import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.util.Date;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    //    Keys.
    @PostMapping("/gen_token")
    public String genToken(@RequestBody AccountDTO accountDTO) {

        return authService.generateToken(accountDTO.getId(), accountDTO.getEmail(), accountDTO.getRole());

    }

    @RequestMapping("/test")
    public ResultVO<String> test() {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("valid token");
        return resultVO;
    }

    @PostMapping("/valid_token")
    public ResultVO<String> validToken(@RequestBody TokenDTO tokenDTO) {
        Claims claims = authService.verify(tokenDTO.getToken());
        if (claims == null) {
            ResultVO<String> resultVO = new ResultVO<>();
            resultVO.setCode(1);
            resultVO.setMsg("invalid token");
            resultVO.setData("{}");
            return resultVO;
        } else {
            ResultVO<String> resultVO = new ResultVO<>();
            resultVO.setCode(0);
            resultVO.setMsg("valid token");
            System.out.println(claims.getSubject());
            resultVO.setData(claims.getSubject());
            return resultVO;
        }

//        System.out.println("valid_token");
//        System.out.println(tokenDTO.getToken());
//        Jws<Claims> jws;

//        try {
//            jws = Jwts.parser()
//                    .setSigningKey(key)
//                    .parseClaimsJws(tokenDTO.getToken());
//            System.out.println(jws.getBody().getSubject());
//            resultVO.setCode(0);
//            resultVO.setMsg("valid token");
//            resultVO.setData(jws.getBody().getSubject());
//        } catch (MissingClaimException mce) {
//            // the parsed JWT did not have the sub field
//            System.out.println("mce");
//        } catch (IncorrectClaimException ice) {
//            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
//            System.out.println("ice");
//        } catch (Exception e) {
//
//        } finally {
//
//        }
//        return resultVO;
    }


}
