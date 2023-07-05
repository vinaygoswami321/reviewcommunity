package com.reviewcommunity.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /*
    *   Generates a JWT token for the user
    *   return a JWT token string
    * */
    public String generateToken(UserDetails userDetails){
            String token =   JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .withSubject(userDetails.getUsername())
                    .sign(Algorithm.HMAC256(secret));
            return token;
    }

    /*
    *   Validates a JWT token using the user details by
    *   decoding the token to get the username (email)
    *   and match it with the username from the user details
    *   and also checks if the token is expired or not
    * */
    public boolean validateToken(String token,UserDetails userDetails){
        String username = JWT.decode(token).getSubject();
        return username != null && username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    /*
    *   Checks if the token is expired or not by getting the expiration date
    *   and time from the token and matching it with the current date and time
    * */
    public boolean isTokenExpired(String token){
         Date expirationDate = JWT.decode(token).getExpiresAt();
         return expirationDate != null && expirationDate.before(new Date());
    }

    /*
    *   Extracts the username (email) from the token using the decode method
    *   and return the extracted username(email)
    * */
    public String extractUserName(String token){
        String username = JWT.decode(token).getSubject();
        return username;
    }
}
