package com.codewithmonu.blog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithmonu.blog.config.AppConstants;
import com.codewithmonu.blog.entities.Role;
import com.codewithmonu.blog.entities.User1;
import com.codewithmonu.blog.exceptions.ResourceNotFoundException;
import com.codewithmonu.blog.payloads.User1Dto;
import com.codewithmonu.blog.repositories.RoleRepo;
import com.codewithmonu.blog.repositories.User1Repo;
import com.codewithmonu.blog.services.User1Service;

@Service
public class User1ServiceImpl implements User1Service {
	
	@Autowired
	private User1Repo user1Repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public User1Dto createUser1(User1Dto user1Dto) {
//		User1 user1=this.dtoToUser1(user1Dto);
//		User1 savedUser1=this.user1Repo.save(user1);
//		return this.user1ToDto(savedUser1);
		User1 user1=this.modelMapper.map(user1Dto, User1.class);
		User1 addedUser1=this.user1Repo.save(user1);
		return this.modelMapper.map(addedUser1, User1Dto.class);
	}

	@Override
//	public User1Dto updateUser1(User1Dto user1, Integer user1Id) {
//	User1 user1Dto=this.user1Repo.findById(user1Id)
//			.orElseThrow(()-> new ResourceNotFoundException("User1", "Id",user1Id));
//	user1.setName(user1Dto.getName());
//	user1.setEmail(user1Dto.getName());
//	user1.setPassword(user1Dto.getPassword());
//	user1.setAbout(user1Dto.getAbout());

//	User1 updateUser1= this.user1Repo.save(user1Dto);
//	User1Dto user1Dto1=this.user1ToDto(updateUser1);
//	return user1Dto1;
	public User1Dto updateUser1(User1Dto user1Dto, Integer user1Id) {
		User1 user1=this.user1Repo.findById(user1Id)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "User1Dto", user1Id));
		user1.setName(user1Dto.getName());
		user1.setEmail(user1Dto.getEmail());
		user1.setPassword(user1Dto.getPassword());
		user1.setAbout(user1Dto.getAbout());
		User1 updateuser1=this.user1Repo.save(user1);
		return this.modelMapper.map(updateuser1, User1Dto.class);
	}

	@Override
	public User1Dto getUser1ById(Integer user1Id) {
		User1 user1=this.user1Repo.findById(user1Id)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "Id", user1Id));
		//return this.user1ToDto(user1);
		return this.modelMapper.map(user1, User1Dto.class);
	}

	@Override
	public List<User1Dto> getAllUsers1() {
		List<User1> users=this.user1Repo.findAll();
		List<User1Dto> user1Dtos=users.stream()
				.map(user1-> this.modelMapper.map(user1, User1Dto.class)).collect(Collectors.toList());
		return user1Dtos;
	}

	@Override
	public void deleteUser1(Integer user1Id) {
		User1 user1=this.user1Repo.findById(user1Id)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "Id", user1Id));
		this.user1Repo.delete(user1);
	}
	
	
	public  User1 dtoToUser1(User1Dto user1Dto) {
		User1 user1=this.modelMapper.map(user1Dto, User1.class);
//		User1 user1= new User1();
//		user1.setId(user1Dto.getId());
//		user1.setName(user1Dto.getName());
//		user1.setEmail(user1Dto.getEmail());
//		user1.setPassword(user1Dto.getPassword());
//		user1.setAbout(user1Dto.getAbout());
		return user1;
	}
	
	public User1Dto user1ToDto(User1 user1) {
		User1Dto user1Dto=this.modelMapper.map(user1, User1Dto.class);
//		User1Dto user1Dto=new User1Dto();
//		user1Dto.setId(user1.getId());
//		user1Dto.setName(user1.getName());
//		user1Dto.setEmail(user1.getEmail());
//		user1Dto.setPassword(user1.getPassword());
//		user1Dto.setAbout(user1.getAbout());
		return user1Dto;
		
	}

	@Override
	public User1Dto registerNewUser1(User1Dto user1Dto) {
		User1 user1= this.modelMapper.map(user1Dto, User1.class);
		
		//encode the password
		user1.setPassword(this.passwordEncoder.encode(user1.getPassword()));
		
		
		//roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER1).get();
		
		user1.getRoles().add(role);
		
		User1 newUser1 = this.user1Repo.save(user1);
		
		return this.modelMapper.map(newUser1, User1Dto.class);
		
		
		//Role role= this.roleRepo.findById(AppConstants.NORMAL_USER1).get();
		//user1.getRoles().add(role);
		//User1 newUser=this.user1Repo.save(user1);
		//return this.modelMapper.map(newUser, User1Dto.class);
		//return user1Dto;
	}

}
