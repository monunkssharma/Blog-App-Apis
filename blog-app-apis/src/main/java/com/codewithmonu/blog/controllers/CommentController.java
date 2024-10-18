package com.codewithmonu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmonu.blog.payloads.ApiResponse;
import com.codewithmonu.blog.payloads.CommentDto;
import com.codewithmonu.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(///////////////////////
			@RequestBody CommentDto commentDto,
			@PathVariable Integer postId)
	{
		CommentDto createComment=this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto>updateComment(
			@RequestBody CommentDto commentDto, 
			@PathVariable Integer postId)
	{
		CommentDto updateComment=this.commentService.updateComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(updateComment, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ApiResponse deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ApiResponse("Comment is successfully deleted!!", true);
	}

}
