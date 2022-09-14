package com.example.questapp.responses;

import com.example.questapp.model.Like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeResponse {
       private Long id;
       private Long userId;
       private Long postId;
	
       public LikeResponse(Like entity) {
		this.id = entity.getId();
		this.userId = entity.getUser().getId();
		this.postId = entity.getPost().getId();
	}
       

   
       
    public static LikeResponse converter(Like like) {
    	 return new LikeResponse (like.getId(), like.getUser().getId(), like.getPost().getId());
    	
    }










}
