package app.netlify.triquetrx.authentication.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import app.netlify.triquetrx.authentication.model.Authentication;
import app.netlify.triquetrx.authentication.repository.AuthenticationRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	AuthenticationRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Authentication> user = repository.findByUsername(username);
		user.orElseThrow(()->new UsernameNotFoundException("User not found"));
		return user.map(UserDetailsImpl::new).get();
	}

}
