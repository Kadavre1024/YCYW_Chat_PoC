package com.ocow.back.dto;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;

@Data
public class DiscussionDto {

	private Long id;
	
	@NonNull
	private String subject;
	
	@NonNull
	private Long clientUserId;
	
	private Long rentalId;
	
	private LocalDateTime createdAt;
}
