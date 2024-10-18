package com.codewithmonu.blog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmonu.blog.payloads.ApiResponse;
import com.codewithmonu.blog.payloads.User1Dto;
import com.codewithmonu.blog.services.User1Service;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class User1Controller {
	@Autowired
	private User1Service user1Service;

	@PostMapping("/")
	public ResponseEntity<User1Dto>createUser1(@Valid @RequestBody User1Dto user1Dto)
	{
		User1Dto createUser1=this.user1Service.createUser1(user1Dto);
		return new ResponseEntity<User1Dto>(createUser1,HttpStatus.CREATED);
		
	}
	@PutMapping("/{user1Id}")
	public ResponseEntity<User1Dto>updateUser1(@Valid @RequestBody User1Dto user1Dto, @PathVariable Integer user1Id){
		User1Dto updateUser1=this.user1Service.updateUser1(user1Dto, user1Id);
		return new  ResponseEntity<User1Dto>(updateUser1, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{user1Id}")
	public ResponseEntity<ApiResponse>deleteUser1(@PathVariable Integer user1Id)
	{
		this.user1Service.deleteUser1(user1Id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User1 deleted successfully", true),HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User1Dto>> getAllUser()
	{
		List<User1Dto> users=this.user1Service.getAllUsers1();
		return ResponseEntity.ok(users);
		
	}
	
	@GetMapping("/{user1Id}")
	public ResponseEntity<User1Dto>getSingleUser1(@PathVariable Integer user1Id)
	{
		User1Dto user1Dto=this.user1Service.getUser1ById(user1Id);
		return new ResponseEntity<User1Dto>(user1Dto,HttpStatus.OK);		
	}
	
	
}
