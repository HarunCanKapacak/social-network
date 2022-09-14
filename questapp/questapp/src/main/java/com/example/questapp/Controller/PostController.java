package com.example.questapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questapp.model.Post;
import com.example.questapp.request.PostCreateRequest;
import com.example.questapp.request.PostUpdateRequest;
import com.example.questapp.responses.PostResponse;
import com.example.questapp.services.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@GetMapping
	public List<PostResponse> getAllPost(@RequestParam Optional<Long> userId){
		return postService.getAllPost(userId);
		}
	
	@GetMapping("/{postId}")
	public Post getOnePost(@PathVariable Long postId) {
		return postService.getOnePostId(postId);
		
	}
	@PostMapping
public Post createOnePost(@RequestBody PostCreateRequest newPostRequest) {
		return postService.createOnePost(newPostRequest);
		
		
	}
	
	@PutMapping("/{postId}")
	public Post updateOnePost(@PathVariable Long postId,@RequestBody PostUpdateRequest postUpdateRequest) {
		return postService.updateOnePostById(postId,postUpdateRequest);
		
		
		
	}
	
	
	@DeleteMapping("/{postId}")
	public void deleteOnePost(@PathVariable Long postId) {
	postService.deleteOnePostId(postId);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
