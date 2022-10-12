package app.netlify.triquetrx.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import app.netlify.triquetrx.authentication.service.impl.AuthenticationEntryPoint;
import app.netlify.triquetrx.authentication.service.impl.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	AuthenticationEntryPoint entryPoint;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/signup").permitAll()
				.antMatchers("/validate").permitAll().antMatchers("/login").permitAll().anyRequest().authenticated()
				.and().exceptionHandling().authenticationEntryPoint(entryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
		return http.build();
	}

	@Bean
	protected PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
