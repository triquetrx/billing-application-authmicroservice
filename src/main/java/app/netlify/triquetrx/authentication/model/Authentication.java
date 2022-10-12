package app.netlify.triquetrx.authentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data @NoArgsConstructor class Authentication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long auid;
	private String username;
	private String password;
	private String roles;
	private long uid;

	public Authentication(String username, String password, String roles, long uid) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.uid = uid;
	}

}
