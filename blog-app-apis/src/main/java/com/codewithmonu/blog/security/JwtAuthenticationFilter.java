package com.codewithmonu.blog.security;

import java.io.IOException;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codewithmonu.blog.exceptions.ApiException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1.get token
		String requestHeader=request.getHeader("Authorization");
		
		//Bearer 2352523sdgsg
		System.out.println(requestHeader);
		String username=null;
		String token=null;
		
		
		if(requestHeader !=null && requestHeader.startsWith("Bearer")) {
			
			token =requestHeader.substring(7);
			
			try{
				username=jwtTokenHelper.getUsernameFromToken(token);
				
			}catch (IllegalArgumentException e)
			
			   {
				 System.out.println("Unable to get Jwt token");
				 throw new ApiException("Unable to get Jwt token");
			   }
			catch (ExpiredJwtException e)
			   {
				 System.out.println("Jwt token has expired");
				 throw new ApiException("Token is Expired");
			   }
			catch(MalformedJwtException e) {
				System.out.println("invalid jwt");
				 throw new ApiException("Invalid JwT Token");
			}
			
			catch (Exception e){
             System.out.println("Invalid JwT Token");
             throw new ApiException("Something wrong with token");
             }
			
		}
		else {
			System.out.println("Jwt token does not begin with Bearer");
			//throw new ApiException("Jwt token does not begin with bearer")
		     }
		
		//Once we get the token, now validate
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			 UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			 if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				 // Authentication 
				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken
						 (userDetails,null, userDetails.getAuthorities());
				 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 
				 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 } else {
			     System.out.println("Invalid jwt token");
		       }
		}else {
			System.out.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	  }
	}
