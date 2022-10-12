package app.netlify.triquetrx.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.netlify.triquetrx.authentication.model.Authentication;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

	Optional<Authentication> findByUsername(String username);
	
}
