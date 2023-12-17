package com.ocow.back.controller;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocow.back.dto.DiscussionDto;
import com.ocow.back.mapper.DiscussionMapper;
import com.ocow.back.model.Discussion;
import com.ocow.back.payload.DiscussionRequest;
import com.ocow.back.service.ClientUserService;
import com.ocow.back.service.DiscussionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("chat")
public class DiscussionController {
	
	@Autowired
	private DiscussionService discService;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private DiscussionMapper discMapper;

	@PostMapping
	public ResponseEntity<?> createChatRoom(@RequestBody @Validated DiscussionRequest discussion){
		Discussion newChatRoom = new Discussion(
				clientUserService.findById(discussion.getClientUserId()), 
				discussion.getSubject()
				);
		
		DiscussionDto res = this.discMapper.toDto(this.discService.create(newChatRoom));
		return ResponseEntity.accepted().body(res);
	}
	
	@GetMapping("/support")
	public ResponseEntity<?> getAllChatRooms(){
		List<Discussion> discussions = this.discService.findAll();
		return ResponseEntity.ok(discMapper.toDto(discussions));
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAllChatRoomsByClientUserId(@PathVariable("id") String id){
		Predicate<Discussion> streamsPredicate = item -> item.getClientUser().getId() == Long.valueOf(id);
		List<Discussion> clientUserDiscussions = this.discService.findAll().stream().filter(streamsPredicate).collect(Collectors.toList());
		return ResponseEntity.ok(discMapper.toDto(clientUserDiscussions));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getChatRoomById(@PathVariable("id") String id){
		return ResponseEntity.ok(discMapper.toDto(this.discService.findById(Long.valueOf(id))));
	}
	
	
	
	
	
	
}
