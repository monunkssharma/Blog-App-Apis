package com.codewithmonu.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY=5 * 60 * 60;
	
	public String secret ="jwtTokenKey";
	
	//retrive username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFormToken(token, Claims::getSubject);
	}
	
	//retrive expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFormToken(token, Claims:: getExpiration);
	}
	
	 public <T> T  getClaimFormToken(String token, Function<Claims, T> claimsResolver) {
		 final Claims claims= getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
		 
	 }
     //for retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//check if the token is expired
	private Boolean isTokenExpired(String token) {
		final Date expiration =getExpirationDateFromToken(token);
		return expiration.before(new Date(  ));
	}
	
	//generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>() ;
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username= getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
