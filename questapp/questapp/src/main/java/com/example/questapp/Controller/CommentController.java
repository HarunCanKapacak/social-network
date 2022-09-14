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

import com.example.questapp.model.Comment;
import com.example.questapp.request.CommentCreateRequest;
import com.example.questapp.request.CommentUpdateRequest;
import com.example.questapp.responses.CommentResponse;
import com.example.questapp.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@GetMapping
	public List<CommentResponse> getAllComment(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId ){
		return commentService.getAllCommentsWithParam(userId,postId);
		
	}
	
	@GetMapping("/{commentId}")
	public Comment getOneComment(@PathVariable Long commentId) {
		return commentService.getById(commentId);
		
	}
	
	@PostMapping
	public Comment createOneComment(@RequestBody CommentCreateRequest request) {
		return commentService.createOneComment(request);
		
		
	}
	
	@PutMapping("/{commentId}")
	public Comment updateOneComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest) {
	return	commentService.updateOneCommentById(commentId,commentUpdateRequest);
		
		
	}
	
	
	@DeleteMapping("/{commentId}")
 public void deleteOneComment(@PathVariable Long commentId) {
		commentService.deleteOneComment(commentId);
	}
	
	
	
	
	
	
	
}



















