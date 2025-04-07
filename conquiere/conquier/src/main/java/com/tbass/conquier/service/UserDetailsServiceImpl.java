package com.tbass.conquier.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("L'utilisateur non trouvé " + username));
		return new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));

	}

}
