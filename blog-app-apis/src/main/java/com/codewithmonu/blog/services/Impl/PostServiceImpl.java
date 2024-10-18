package com.codewithmonu.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithmonu.blog.entities.Category;
import com.codewithmonu.blog.entities.Post;
import com.codewithmonu.blog.entities.User1;
import com.codewithmonu.blog.exceptions.ResourceNotFoundException;
import com.codewithmonu.blog.payloads.PostDto;
import com.codewithmonu.blog.repositories.CategoryRepo;
import com.codewithmonu.blog.repositories.PostRepo;
import com.codewithmonu.blog.repositories.User1Repo;
import com.codewithmonu.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private User1Repo user1Repo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public PostDto createPost(PostDto postDto, Integer user1Id, Integer categoryId) {
		
		User1 user1=this.user1Repo.findById(user1Id)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "User1Id", user1Id));
		
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser1(user1);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatepost=this.postRepo.save(post);
		return this.modelMapper.map(updatepost, PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);

	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> allPosts=this.postRepo.findAll();
		List<PostDto> postDtos=allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		List<Post>posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper
				.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser1(Integer user1Id) {
		User1 user1=this.user1Repo.findById(user1Id)
				.orElseThrow(()-> new ResourceNotFoundException("User1", "User1Id", user1Id));
		List<Post> posts=this.postRepo.findByUser1(user1);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper
				.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> serachPosts(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

}
