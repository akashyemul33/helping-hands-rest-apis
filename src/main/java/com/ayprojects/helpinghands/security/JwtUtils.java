package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(AppConstants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public String extractUserName(String token){
        return  extractClaim(token,Claims::getSubject);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public AccessTokenModel generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    public boolean validateToken(String token, DhUser user){
        final String username=extractUserName(token);
        return (username.equals(user.getMobileNumber())||username.equals(user.getEmailId())) && !isTokenExpired(token);
    }
    private AccessTokenModel createToken(Map<String,Object> claims, String subject){
        String jwtString = Jwts.builder().setClaims(claims)
                .setIssuer(AppConstants.JWT_TOKEN_ISSUER)
                .setSubject(subject)
                .setAudience(AppConstants.JWT_TOKEN_AUDIENCE)
                .setExpiration(new Date(System.currentTimeMillis()+ AppConstants.JWT_TOKEN_EXPIRATION_VALUE))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,AppConstants.JWT_SECRET_KEY).compact();

        AccessTokenModel accessTokenModel = new AccessTokenModel();
        accessTokenModel.setAccess_token(jwtString);
        accessTokenModel.setExpires_in(AppConstants.JWT_TOKEN_EXPIRATION_VALUE);
        accessTokenModel.setRefresh_token("");
        accessTokenModel.setScope("");
        accessTokenModel.setToken_type("");
        return accessTokenModel;
    }
}
