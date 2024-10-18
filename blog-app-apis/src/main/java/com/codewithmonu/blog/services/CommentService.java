package com.codewithmonu.blog.services;

import com.codewithmonu.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Integer postId);
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	void deleteComment(Integer commentId);
	
	

}
