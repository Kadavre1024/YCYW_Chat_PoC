package com.ocow.back.payload;

import lombok.Data;
import lombok.NonNull;

@Data
public class DiscussionRequest {

	@NonNull
	private String subject;
	
	@NonNull
	private Long clientUserId;
	
	private Long rentalId;
}
