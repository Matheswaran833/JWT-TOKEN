package com.SpringBoot.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.SpringBoot.Entity.UserEntity;
import com.SpringBoot.Repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetch user from database
	UserEntity user=	userrepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new User(user.getName(),user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}

}
