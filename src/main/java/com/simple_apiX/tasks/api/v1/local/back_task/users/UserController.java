package com.simple_apiX.tasks.api.v1.local.back_task.users;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_apiX.tasks.api.v1.local.back_task.users.adapters.payloads.UserDto;



@RestController
@RequestMapping("/users")
public class UserController {
    
    private String myClassName = UserController.class.getName();


    @Autowired
    UserService userService;


	@GetMapping("")
	public ResponseEntity<?> getAll()
	{
		Object resp = userService.getAll();
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}


	@PostMapping("")
	public ResponseEntity<?> save(
		@Valid @RequestBody UserDto payload
	)
	{
		Object resp = userService.save( payload );
		return new ResponseEntity<>( resp, HttpStatus.OK );
	}

}
