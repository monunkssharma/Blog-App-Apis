package com.codewithmonu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmonu.blog.exceptions.ApiException;
import com.codewithmonu.blog.payloads.JwtAuthRequest;
import com.codewithmonu.blog.payloads.JwtAuthResponse;
import com.codewithmonu.blog.payloads.User1Dto;
import com.codewithmonu.blog.security.JwtTokenHelper;
import com.codewithmonu.blog.services.User1Service;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private User1Service user1Service;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
	{
		this.authenticate(request.getUsername(), request.getPassword());
		 UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		 
		 String token = this.jwtTokenHelper.generateToken(userDetails);
		 
		 JwtAuthResponse response = new JwtAuthResponse();
		 response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK); 
	}
	
	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		
		}catch (BadCredentialsException e){
			System.out.println("Invalid Details!!");
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	//register new user API
	@PostMapping("/register")
	public ResponseEntity<User1Dto> registerUser1(@RequestBody User1Dto user1Dto){
		
		User1Dto registerdUser1 = this.user1Service.registerNewUser1(user1Dto);
		
		return new ResponseEntity<User1Dto>(registerdUser1, HttpStatus.CREATED );
		
	}
	
	
}
