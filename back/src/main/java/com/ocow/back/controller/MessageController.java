package com.ocow.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ocow.back.dto.MessageDto;
import com.ocow.back.mapper.MessageMapper;
import com.ocow.back.model.Message;
import com.ocow.back.service.MessageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("message")
public class MessageController {

	private final SimpMessagingTemplate template;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@Autowired
	private MessageService messageService;

    MessageController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/send/{id}")
    @SendTo("/ws/send/{id}")
    public MessageDto onReceivedMesage(@DestinationVariable("id") String id, @Validated MessageDto message){
    	Message newMessage = this.messageService.create(this.messageMapper.toEntity(message));
    	
        return this.messageMapper.toDto(newMessage);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllMessagesByDiscussionId(@PathVariable("id") String id){
    	List<MessageDto> messages = this.messageMapper.toDto(this.messageService.findAllByDiscussionId(Long.valueOf(id)));
    	return ResponseEntity.ok(messages);
    }
}
