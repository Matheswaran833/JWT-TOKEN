package com.SpringBoot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.SpringBoot.Entity.UserEntity;
import com.SpringBoot.Repository.UserRepository;

public class CustonUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fetch user from database
	UserEntity user=	userrepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new User(user.getName(),user.getPassword(),null);
	}

}
