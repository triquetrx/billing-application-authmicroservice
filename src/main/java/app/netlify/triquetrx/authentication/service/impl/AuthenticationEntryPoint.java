package app.netlify.triquetrx.authentication.service.impl;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint
		implements org.springframework.security.web.AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 5508423577502358027L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED_ACCESS");

	}

}
