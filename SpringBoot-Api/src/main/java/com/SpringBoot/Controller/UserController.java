package com.SpringBoot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Entity.UserEntity;
import com.SpringBoot.Exception.ResourceNotFoundException;
import com.SpringBoot.Repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
/*	@GetMapping
	public String getUsers() {
		return "Hello";
	} */
	
	@GetMapping
	public List<UserEntity> getUsers() {
	/*	return Arrays.asList(new User(1L,"madhu","madhu123@gmail.com"),new User(2L,"Raj","raj123@gmail.com"),
				             new User (3L,"yash","yash123@gmail.com")); */
		return userrepo.findAll();
	}
	
	@PostMapping
	public UserEntity postUser(@RequestBody UserEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userrepo.save(user);
	}
	
	@GetMapping("/{id}")
	public UserEntity getUserById(@PathVariable Long id) {
		return userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:"+id));
	}
	
	@PutMapping("/{id}")
	public UserEntity updateUserById(@PathVariable Long id,@RequestBody UserEntity user) {
		UserEntity userData=userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:"+id));
		userData.setName(user.getName());
		userData.setEmail(user.getEmail());
		return userrepo.save(userData);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		UserEntity userData=userrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id:"+id));
		userrepo.delete(userData);
		return ResponseEntity.ok().build();
	}

}
