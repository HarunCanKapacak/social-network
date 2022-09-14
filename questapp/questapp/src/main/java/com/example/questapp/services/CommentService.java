package com.example.questapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.example.questapp.Repository.CommentRepository;
import com.example.questapp.model.Comment;
import com.example.questapp.model.Post;
import com.example.questapp.model.User;
import com.example.questapp.request.CommentCreateRequest;
import com.example.questapp.request.CommentUpdateRequest;
import com.example.questapp.responses.CommentResponse;

@Service

public class CommentService {

	private  CommentRepository commentRepository;
    private  UserService userService;
    private PostService postService;
	public CommentService(CommentRepository commentRepository,UserService userService,PostService postService) {
		this.userService = userService;
		this.commentRepository = commentRepository;
	    this.postService = postService;
	}

	public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent() && postId.isPresent()) {
			comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		}else if(userId.isPresent()) {
			comments = commentRepository.findByUserId(userId.get());
		}else if(postId.isPresent()) {
			comments = commentRepository.findByPostId(postId.get());
		}else
			comments = commentRepository.findAll();
		return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
	}

	public Comment getById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUser(request.getUserId());
        Post post = postService.getOnePostId(request.getPostId());
	  if(user != null && post != null) {
		  Comment commentToSave = new Comment();
		  commentToSave.setId(request.getId());
		  commentToSave.setPost(post);
		  commentToSave.setUser(user);
		  commentToSave.setText(request.getText());
		  return commentRepository.save(commentToSave);
		  
		  
	  }else
		 return null;
	
	
	
	}

	public Comment updateOneCommentById(Long commentId,CommentUpdateRequest commentUpdateRequest) {
	
		Optional<Comment> comment = commentRepository.findById(commentId); 
		if(comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			
           commentToUpdate.setText(commentUpdateRequest.getText());
			return  commentRepository.save(commentToUpdate);
		}else
			return null;
		
	}

	public void deleteOneComment(Long commentId) {
		commentRepository.deleteById(commentId);
		
	}

	
	
	
	
	
	
}

















