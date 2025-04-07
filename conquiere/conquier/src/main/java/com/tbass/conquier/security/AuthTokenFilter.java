package com.tbass.conquier.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tbass.conquier.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.prefix}")
	private String prefix;
	@Value("${jwt.header}")
	private String header;

	private final UserDetailsServiceImpl userDetailsService;

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String jwtTokenRequest = request.getHeader(header);

		if (jwtTokenRequest != null && jwtTokenRequest.startsWith(prefix)) {

			String jwtToken = jwtTokenRequest.substring(prefix.length());
			String username = jwtUtil.getUsernameFromToken(jwtToken);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			System.out.println(userDetails.getAuthorities().toString());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);

		}

		filterChain.doFilter(request, response);

	}

}
