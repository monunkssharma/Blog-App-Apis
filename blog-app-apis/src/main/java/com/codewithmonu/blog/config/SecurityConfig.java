package com.codewithmonu.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codewithmonu.blog.security.CustomUserDetailService;
import com.codewithmonu.blog.security.JwtAuthenticationEntryPoint;
import com.codewithmonu.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc 
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		 http.csrf( csrf -> csrf.disable())
         .cors( cors-> cors.disable())
         .authorizeHttpRequests( auth-> auth
        		// .requestMatchers(AppConstants.PUBLIC_URL).permitAll()
         .requestMatchers(HttpMethod.GET).permitAll()
         .requestMatchers("/api/v1/auth/**").permitAll()
         .anyRequest().authenticated())
         .exceptionHandling( ex-> ex
         .authenticationEntryPoint(jwtAuthenticationEntryPoint))
         .sessionManagement( session -> session
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		     
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		return defaultSecurityFilterChain;
	}
	
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    //This method used to authenticate username and password
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
	    		throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	}