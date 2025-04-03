package com.tbass.conquier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tbass.conquier.security.AuthTokenFilter;
import com.tbass.conquier.security.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final AuthTokenFilter authTokenFilter;
	
	public SecurityConfig(AuthTokenFilter authTokenFilter) {
		this.authTokenFilter = authTokenFilter;
	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	protected AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {

		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder).and().build();
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) 
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/register").permitAll()
				.requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
				.requestMatchers("/api/auth").permitAll()
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				.requestMatchers("/api/user/**").hasRole("USER")
//				.anyRequest().permitAll()) Decommenter pour les test
				.anyRequest().authenticated())  
//				.formLogin(form -> form.loginPage("/login").permitAll())
				.logout(logout -> logout.permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}