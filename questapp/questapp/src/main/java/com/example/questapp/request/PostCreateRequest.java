package com.example.questapp.request;

import lombok.Data;

@Data
public class PostCreateRequest {

	
	Long id;
	String text;
	String title;
	Long userId;
	
	
}
