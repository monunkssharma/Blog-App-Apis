package com.codewithmonu.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4, message="Min size of category title is 4")
	private String categoryTittle;
	
	@NotBlank
	@Size(min=10, message="Min size of category desc is 10")
	private String categoryDescription;

}
