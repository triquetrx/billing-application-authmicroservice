package app.netlify.triquetrx.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class ValidationResponse {
	
	private boolean status;
	private String username;

}
