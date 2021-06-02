package com.gabriel.marketplace.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    //It should be a hard one!
    private static final String KEY = "market";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        // We validate that it has the correct user
        // That it has not expired.
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    //We get the username rom the claims
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    // We verify that it has not expired
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token){
        // We add the KEY
        // It verifies that the signature is correct
        // We get the claims (the body of the WebToken)
        return  Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
