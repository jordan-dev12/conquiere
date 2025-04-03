package com.tbass.conquier.service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserRegistrationRequestDto;
import com.tbass.conquier.dtos.UserRegistrationResponseDto;
import com.tbass.conquier.dtos.UsersResponseDto;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.enums.Role;
import com.tbass.conquier.mappers.UserMapper;
import com.tbass.conquier.repositories.UserRepository;
import com.tbass.conquier.utility.PaginationUtils;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}

	@Override
	public UserRegistrationResponseDto save(UserRegistrationRequestDto client) {

		UserEntity userEntity = userMapper.toEntity(client);
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		addRole(userEntity, Role.USER.getValue());
		return userMapper.toDto(userRepository.save(userEntity));
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserRegistrationResponseDto getById(long id) {
		UserEntity userEntity = userRepository.getReferenceById(id);
		return userMapper.toDto(userEntity);
	}

	@Override
	public UsersResponseDto getClients(PaginationDto pagination) {

		PageRequest pageable = PaginationUtils.getPageable(pagination);
		Page<UserEntity> usersEntities = userRepository.findAll(pageable);
		UsersResponseDto users = new UsersResponseDto();
		users.setUsers(usersEntities.stream().map(entity -> userMapper.toDto(entity)).toList());
		users.setTotals(usersEntities.getNumberOfElements());

		return users;

	}

	@Override
	public UserRegistrationResponseDto update(UserRegistrationRequestDto user) {

		UserEntity entity = userMapper.toEntity(user);
		return userMapper.toDto(userRepository.save(entity));
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(mail);
		if (userEntity == null) {
			throw new UsernameNotFoundException(mail);
		}
		return new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));

	}

	private void addRole(UserEntity userEntity, String role) {
		if (userEntity.getRoles() == null) {
			userEntity.setRoles(new ArrayList<>());
		}
		userEntity.getRoles().add(role);
	}
}
