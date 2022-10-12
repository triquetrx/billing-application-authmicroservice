package app.netlify.triquetrx.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.triquetrx.authentication.dto.LoginDTO;
import app.netlify.triquetrx.authentication.dto.SignupDTO;
import app.netlify.triquetrx.authentication.exceptions.UsernameAlreadyExistsException;
import app.netlify.triquetrx.authentication.model.ValidationResponse;
import app.netlify.triquetrx.authentication.service.UserRequestService;
import app.netlify.triquetrx.authentication.util.JwtUtil;

@RestController
public class AuthenticationController {

	@Autowired
	UserRequestService requestService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil util;

	@Autowired
	UserDetailsService userDetailsService;

	@CrossOrigin
	@GetMapping("/validate")
	public ResponseEntity<?> validateAuthentication(@RequestHeader(name = "Authorization") String token) {
		String tokenDummy = token.substring(7);
		String username = util.getUsernameFromToken(tokenDummy);
		if(username != null) {
			UserDetails userData = userDetailsService.loadUserByUsername(username);
			if (util.validateToken(tokenDummy, userData)) {
				return new ResponseEntity<>(new ValidationResponse(true,username), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new ValidationResponse(false, username), HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
		try {
			authenticate(loginDTO.getUsername(), loginDTO.getPassword());
			final UserDetails userRequest = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			final String token = util.generateToken(userRequest);
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (DisabledException | BadCredentialsException e) {
			return new ResponseEntity<>("Bad Credentials", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
		try {
			requestService.createNewUser(signupDTO);
			return new ResponseEntity<>("User Created", HttpStatus.OK);
		} catch (UsernameAlreadyExistsException e) {
			return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
		}
	}

	private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
