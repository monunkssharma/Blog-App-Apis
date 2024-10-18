package com.codewithmonu.blog.services;

import java.util.List;

import com.codewithmonu.blog.payloads.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer user1Id, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	PostDto getPostById(Integer postId);
	void deletePost(Integer postId);
	List<PostDto> getAllPosts();
	
	List<PostDto>getPostByCategory(Integer categoryId);
	List<PostDto>getPostByUser1(Integer user1Id);
	
	List<PostDto> serachPosts(String keywords);

}
