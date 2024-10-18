package com.codewithmonu.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmonu.blog.entities.Category;
import com.codewithmonu.blog.entities.Post;
import com.codewithmonu.blog.entities.User1;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser1(User1 user1);
	List<Post> findByCategory(Category category);
	
}
