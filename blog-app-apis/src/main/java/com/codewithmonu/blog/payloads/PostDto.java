package com.codewithmonu.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.codewithmonu.blog.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
		
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private User1Dto user1;
	
	private Set<CommentDto> comments =new HashSet<>();
	
	

}
