package com.example.questapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questapp.model.Post;

public interface PostRepository  extends JpaRepository<Post, Long>{

	List<Post> findByUserId(Long userId);

}
