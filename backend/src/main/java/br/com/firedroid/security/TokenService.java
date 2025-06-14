package br.com.firedroid.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.firedroid.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("site-animes")
					.withSubject(user.getUsername())
					.withExpiresAt(genExpirationDate())
					.withClaim("role", user.getRole().toString())
					.withClaim("id", user.getId())
					.withClaim("email", user.getEmail())
					.withClaim("username", user.getUsername())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("site-animes")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new IllegalArgumentException("Token inválido ou expirado", e);
		}
	}
	public Long extractUserId(String token) {
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        DecodedJWT decodedJWT = JWT.require(algorithm)
	                .withIssuer("site-animes")
	                .build()
	                .verify(token);
	        return decodedJWT.getClaim("id").asLong();
	    } catch (JWTVerificationException e) {
	        throw new IllegalArgumentException("Token inválido ou expirado", e);
	    }
	}
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}