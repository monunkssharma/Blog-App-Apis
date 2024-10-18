package com.codewithmonu.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithmonu.blog.entities.User1;
import com.codewithmonu.blog.exceptions.ResourceNotFoundException;
import com.codewithmonu.blog.repositories.User1Repo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private User1Repo user1Repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading user from database by username.
		
		User1 user1 = this.user1Repo.findByEmail(username)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "email :"+ username,0));
		return user1;
	}

}
