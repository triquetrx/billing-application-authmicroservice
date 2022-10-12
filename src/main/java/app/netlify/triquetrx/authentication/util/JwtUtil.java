package app.netlify.triquetrx.authentication.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil implements Serializable {

	private static final long serialVersionUID = -3368817688440878891L;

	public static long jwtExpiry = 1 * 60 * 60;

	@Value("${secretKey}")
	private String secretKey;

	public String getUsernameFromToken(String token) {
		try {
			return getClaimFromToken(token, Claims::getSubject);
		} catch (Exception e) {
			return null;
		}
	}

	public Date getExpiryFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		// TODO Auto-generated method stub
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpiryFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return generateNewToken(claims, userDetails.getUsername());
	}

	private String generateNewToken(Map<String, Object> claims, String username) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (jwtExpiry * 1000)))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		try {

			final String username = getUsernameFromToken(token);
			return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
		} catch (SignatureException e) {
			return false;
		}
	}

}
