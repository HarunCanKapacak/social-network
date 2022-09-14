package com.example.questapp.services;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.questapp.Repository.PostRepository;
import com.example.questapp.model.Post;
import com.example.questapp.model.User;
import com.example.questapp.request.PostCreateRequest;
import com.example.questapp.request.PostUpdateRequest;
import com.example.questapp.responses.PostResponse;

@Service
public class PostService {
  
	
	private UserService userService;
	private PostRepository postRepository;

	public PostService(PostRepository postRepository,UserService userService) {
     	this.userService = userService ;
		this.postRepository = postRepository;
	}

	public List<PostResponse> getAllPost(Optional<Long> userId) {
	List<Post> list;
		if(userId.isPresent()) {
			list = postRepository.findByUserId(userId.get());
			
		}
		list = postRepository.findAll();
		return  list.stream().map(p -> new PostResponse(p)).collect(Collectors.toList());
	}
	   

	public Post getOnePostId(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}



	

	public Post createOnePost(PostCreateRequest newPostRequest) {
	User user =	userService.getOneUser(newPostRequest.getUserId());
	if(user == null)
		return null;
	Post toSave = new Post();
	toSave.setId(newPostRequest.getId());
	toSave.setText(newPostRequest.getText());
	toSave.setTitle(newPostRequest.getTitle());
	toSave.setUser(user);
	return postRepository.save(toSave);
	
	
	}



	public void deleteOnePostId(Long postId) {
		postRepository.deleteById(postId);
		
	}

	public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = postRepository.findById(postId);
		if(post.isPresent()) {
			Post updatePost = post.get();
			
			updatePost.setText(postUpdateRequest.getText());
			updatePost.setTitle(postUpdateRequest.getTitle());
			postRepository.save(updatePost);
			return updatePost;
		}
	return null;
	}
	

	
	
	
}






















