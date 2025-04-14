package com.tbass.conquier.service;

import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	@Override
	public UserRegistrationResponseDto registerUsers(UserRegistrationRequestDto client) {

		UserEntity userEntity = userMapper.toEntity(client);
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		addRole(userEntity, Role.USER.getValue());
		return userMapper.toDto(userRepository.save(userEntity));
	}

	@Override
	public void delete(long id) {
		findById(id);
		userRepository.deleteById(id);
	}

	@Override
	public UserRegistrationResponseDto getById(long id) {
		UserEntity userEntity = findById(id);
		return userMapper.toDto(userEntity);
	}

	@Override
	public UsersResponseDto getClients(PaginationDto pagination) {

		PageRequest pageable = PaginationUtils.getPageable(pagination);
		Page<UserEntity> usersEntities = userRepository.findAll(pageable);
		UsersResponseDto usersResponse = UsersResponseDto.builder()
			.users(usersEntities.stream().map(entity -> userMapper.toDto(entity)).toList())
			.totals(usersEntities.getNumberOfElements())
			.build();
		return usersResponse;

	}

	@Override
	public UserRegistrationResponseDto update(UserRegistrationRequestDto user) {

		UserEntity entity = userMapper.toEntity(user);
		return userMapper.toDto(userRepository.save(entity));
	}

	@Override
	public void deleteAll() {
		this.userRepository.deleteAll();
	}

	@Override
	public UserRegistrationResponseDto getByUsername(String username) {
		UserEntity userEntityResponse = userRepository.findByEmail(username)
			.orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'username: " + username));
		return userMapper.toDto(userEntityResponse);
	}

	private void addRole(UserEntity userEntity, String role) {
		if (userEntity.getRoles() == null) {
			userEntity.setRoles(new HashSet<>());
		}
		userEntity.getRoles().add(role);
	}

	private UserEntity findById(long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
	}

}
