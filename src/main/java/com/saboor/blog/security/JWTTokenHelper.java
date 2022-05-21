package com.saboor.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    private String secret = "jwtTokenKey";

    // Retrieve Username from JWT Token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

     // Retrieve expiration date from JWT Token
     public Date getExpirationDateFromToken(String token){
         return getClaimFromToken(token,Claims :: getExpiration);
     }
     public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we will need the secret pass key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //Check if the token has expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //Generate token for User
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims =  new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    /* While creating the Toke :-
      1. Define claims of the token like Issuer, Expiration, Subject and the ID
      2. Sign the JWT using the HS512 algorithm and the secret key.
      3. According to JWS Compact Serialization, perform compaction of the JWT to a URL-safe String
     */
    private String doGenerateToken(Map<String,Object> claims , String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
