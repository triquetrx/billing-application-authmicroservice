package app.netlify.triquetrx.authentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.netlify.triquetrx.authentication.dto.SignupDTO;
import app.netlify.triquetrx.authentication.exceptions.UsernameAlreadyExistsException;
import app.netlify.triquetrx.authentication.model.Authentication;
import app.netlify.triquetrx.authentication.repository.AuthenticationRepository;
import app.netlify.triquetrx.authentication.service.UserRequestService;

@Service
public class UserRequestServiceImpl implements UserRequestService {

	@Autowired
	AuthenticationRepository authenticationRepository;

	@Override
	public Authentication createNewUser(SignupDTO dto) throws UsernameAlreadyExistsException {
		if(authenticationRepository.findByUsername(dto.getUsername()).isEmpty()) {			
			return authenticationRepository
					.save(new Authentication(dto.getUsername(), encoder().encode(dto.getPassword()), "ROLE_USER", dto.getUid()));
		}
		throw new UsernameAlreadyExistsException("Username Already Exists");
	}
	
	private PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
