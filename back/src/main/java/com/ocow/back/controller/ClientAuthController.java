package com.ocow.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocow.back.dto.ClientUserDto;
import com.ocow.back.mapper.ClientUserMapper;
import com.ocow.back.model.ClientUser;
import com.ocow.back.payload.JwtResponse;
import com.ocow.back.payload.LoginRequest;
import com.ocow.back.payload.MessageResponse;
import com.ocow.back.payload.RegisterRequest;
import com.ocow.back.security.JwtUtil;
import com.ocow.back.security.UserDetailsImpl;
import com.ocow.back.security.UserDetailsServiceImpl;
import com.ocow.back.service.ClientUserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class ClientAuthController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private ClientUserMapper userMapper;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
		
		ClientUser user = clientUserService.findByEmail(loginRequest.getEmail());
		
		if(user == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid email"));
		}
		
		if(!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid password"));
		}
		
		Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(authentication.getName());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        
        JwtResponse resp = new JwtResponse(jwt, userDetails.getUsername(), userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getPassword(), userDetails.getAuthorities());
        System.out.println("jwt : "+ resp);
        return ResponseEntity.ok(resp);
	}
	
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
		
		if (clientUserService.existByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }
		
		ClientUser user = new ClientUser(
				null, registerRequest.getFirstName(),
				registerRequest.getLastName(),
				registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()), null, null, null, null
                );
		
		clientUserService.register(user);
		
		return ResponseEntity.ok().body(new MessageResponse("User "+user.getFirstName()+user.getLastName()+" is registered"));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getUserDetails(Authentication auth){
		ClientUserDto userAuth = userMapper.toDto(clientUserService.findByEmail(auth.getName()))   ;
		return ResponseEntity.ok().body(userAuth);
	}
}
