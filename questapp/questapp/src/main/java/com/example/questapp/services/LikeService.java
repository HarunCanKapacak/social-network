package com.example.questapp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.questapp.Repository.LikeRepository;
import com.example.questapp.model.Like;
import com.example.questapp.model.Post;
import com.example.questapp.model.User;
import com.example.questapp.request.LikeCreateRequest;
import com.example.questapp.responses.LikeResponse;

@Service
public class LikeService {
	private LikeRepository likeRepository;
	private UserService userService;
	private PostService postService;

	public LikeService(LikeRepository likeRepository, UserService userService, 
			PostService postService) {
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}

	 public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
	        List<Like> list;
	        if(userId.isPresent() && postId.isPresent()){
	            list= likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
	        }
	        else if(userId.isPresent()){
	            list= likeRepository.findByUserId(userId.get());
	        }
	        else if(postId.isPresent()){
	            list= likeRepository.findByPostId(postId.get());
	        }else
	            list= likeRepository.findAll();
	          return list.stream().map(like->new LikeResponse(like)).collect(Collectors.toList());
	    }

	public LikeResponse getOneLikeById(Long LikeId) {
		  Like like = likeRepository.findById(LikeId).orElse(null);
		 return LikeResponse.converter(like);
	}

	public LikeResponse createOneLike(LikeCreateRequest request) {
		User user = userService.getOneUser(request.getUserId());
		Post post = postService.getOnePostId(request.getPostId());
		if(user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setPost(post);
			likeToSave.setUser(user);
			 likeRepository.save(likeToSave);
			 return LikeResponse.converter(likeToSave);
		}else		
			return null;
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);
	}
	
}






















