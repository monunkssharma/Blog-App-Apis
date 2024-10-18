package com.codewithmonu.blog.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithmonu.blog.entities.Comment;
import com.codewithmonu.blog.entities.Post;
import com.codewithmonu.blog.exceptions.ResourceNotFoundException;
import com.codewithmonu.blog.payloads.CommentDto;
import com.codewithmonu.blog.repositories.CommentRepo;
import com.codewithmonu.blog.repositories.PostRepo;
import com.codewithmonu.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepo postRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment=this.commentRepo.save(comment);
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
		comment.setContent(commentDto.getContent());
		Comment updateComment=this.commentRepo.save(comment);
		return this.modelMapper.map(updateComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(comment);
	}

}
