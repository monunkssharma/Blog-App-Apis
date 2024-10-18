package com.codewithmonu.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithmonu.blog.config.AppConstants;
import com.codewithmonu.blog.entities.Role;
import com.codewithmonu.blog.repositories.RoleRepo;


@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("raj@9090"));
		
		try {
			
			Role role= new Role();
			role.setId(AppConstants.ADMIN_USER1);
			role.setName("ADMIN_USER1");
			
			
			Role role1= new Role();
			role1.setId(AppConstants.NORMAL_USER1);
			role1.setName("NORMAL_USER1");
			
			List<Role> roles=List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	    

}
