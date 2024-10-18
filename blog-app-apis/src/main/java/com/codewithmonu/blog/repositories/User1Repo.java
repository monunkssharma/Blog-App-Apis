package com.codewithmonu.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmonu.blog.entities.User1;

public interface User1Repo extends JpaRepository<User1, Integer> {

	Optional<User1> findByEmail(String email);
}
