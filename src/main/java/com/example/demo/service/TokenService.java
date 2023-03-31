package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    //create and verify token
    @Value("${app.token.secret}")
    private String secret;
    @Value("${app.token.issuer}")
    private String issuer;
    public String tokenize(User user){
        String token ="";
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expire = calendar.getTime();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("principal", user.getId()) //addUserID
                    .withClaim("role", "USER") //add role
                    .withExpiresAt(expire) //add expire time
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;

    }

    public DecodedJWT isValid(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer(issuer)
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException exception){
            return null;
        }
        return decodedJWT;
    }
}

