package com.tbass.conquier.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	private static final String USER_DETAILS = "userDetails";
	private static final String USERNAME = "username";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access.token.expiration}")
	private long jwtExpirationInMs;

	@Value("${jwt.refresh.token.expiration}")
	private int jwtRefreshTokenExpiration;

	public String getUsernameFromToken(final String token) {
		Object claimFromToken = getClaimFromToken(token, body -> body.get(USERNAME));
		return (String) claimFromToken;
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(final String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(final String token) {
		Key key = calculateKey();
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

	}

	// check if the token has expired
	private Boolean isTokenExpired(final String token) {
		try {
			Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	// generate token for user
	public String generateAccessToken(final UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(USER_DETAILS, userDetails);
		claims.put(USERNAME, userDetails.getUsername());
		return doGenerateToken(claims, userDetails.getUsername(), jwtExpirationInMs);
	}

	public String generateRefreshToken(final UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
//		claims.put(USER_DETAILS, userDetails);
		claims.put(USERNAME, userDetails.getUsername());
		return doGenerateToken(claims, userDetails.getUsername(), jwtRefreshTokenExpiration);
	}

	private String doGenerateToken(final Map<String, Object> claims, final String subject, long jwtExperation) {

		Key key = calculateKey();

		Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
		Instant expiration = issuedAt.plus(jwtExperation, ChronoUnit.MILLIS);

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(Date.from(issuedAt))
			.setExpiration(Date.from(expiration))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

	}

	private Key calculateKey() {
		byte[] keyBytes = secret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// validate token
	public boolean validToken(final String token, final String userName) {
		if (isTokenExpired(token)) {
			return false;
		}
		String username = getUsernameFromToken(token);
		return username.equalsIgnoreCase(userName);
	}

}
