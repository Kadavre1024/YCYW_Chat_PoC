package com.ocow.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.ocow.back.service.RentalService;

/**@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/rental")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;

	@GetMapping("client/{id}")
	private ResponseEntity<?> findAllByClientId(@PathVariable("id") String id){
		return ResponseEntity.ok(this.rentalService.findAllByClientUserId(Long.valueOf(id)));
	}
}**/
