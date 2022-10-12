package app.netlify.triquetrx.authentication.service;

import org.springframework.stereotype.Service;

import app.netlify.triquetrx.authentication.dto.SignupDTO;
import app.netlify.triquetrx.authentication.exceptions.UsernameAlreadyExistsException;
import app.netlify.triquetrx.authentication.model.Authentication;

@Service
public interface UserRequestService {

	Authentication createNewUser(SignupDTO dto) throws UsernameAlreadyExistsException;

}
