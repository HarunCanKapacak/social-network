package com.example.questapp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questapp.request.LikeCreateRequest;
import com.example.questapp.responses.LikeResponse;
import com.example.questapp.services.LikeService;





@RestController
@RequestMapping("/likes")
public class LikeController {
	
	


	private LikeService likeService;

	
	
	public LikeController(LikeService likeService) {
		super();
		this.likeService = likeService;
	}

	@GetMapping()
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return likeService.getAllLikesWithParam(userId, postId);
    }
	
	@PostMapping
	public LikeResponse createOneLike(@RequestBody LikeCreateRequest request) {
		return likeService.createOneLike(request);
	}
	
	@GetMapping("/{likeId}")
	public LikeResponse getOneLike(@PathVariable Long likeId) {
		return likeService.getOneLikeById(likeId);
	}
	
	@DeleteMapping("/{likeId}")
	public void deleteOneLike(@PathVariable Long likeId) {
		likeService.deleteOneLikeById(likeId);
	}
}





















