package com.example.questapp.responses;

import com.example.questapp.model.Post;

import lombok.Data;

@Data
public class PostResponse {
	Long id;
	Long userId;
	String userName;
	String title;
    String text;
	public PostResponse (Post entity) {
		 this.id = entity.getId();
		 this.userId = entity.getUser().getId();
		 this.userName = entity.getUser().getUserName();
		 this.title = entity.getText();

	
	
	}
    
    
    
}
