package app.netlify.triquetrx.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class LoginDTO {
	
	private String username;
	private String password;

}
