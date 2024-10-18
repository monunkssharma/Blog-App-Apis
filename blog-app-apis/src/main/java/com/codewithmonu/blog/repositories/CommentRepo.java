package com.codewithmonu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmonu.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
