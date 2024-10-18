package com.codewithmonu.blog.services;

import java.util.List;

import com.codewithmonu.blog.payloads.User1Dto;

public interface User1Service {
	
  User1Dto registerNewUser1(User1Dto user1);	
	
  User1Dto createUser1(User1Dto user1Dto);
  
  User1Dto updateUser1(User1Dto user1Dto , Integer user1Id);
  
  User1Dto getUser1ById(Integer userId);
  
  List<User1Dto>getAllUsers1();
  
  void deleteUser1(Integer user1Id);
}
