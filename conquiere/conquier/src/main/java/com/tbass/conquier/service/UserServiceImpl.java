package com.tbass.conquier.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.dtos.UserDto;
import com.tbass.conquier.dtos.UsersResponseDto;
import com.tbass.conquier.entity.UserEntity;
import com.tbass.conquier.mappers.UserMapper;
import com.tbass.conquier.repositories.UserRepository;
import com.tbass.conquier.utility.PaginationUtils;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@Override
	public UserDto save(UserDto client) {

		UserEntity entity = userMapper.toEntity(client);
		return userMapper.toDto(userRepository.save(entity));
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDto getById(long id) {
		UserEntity userEntity = userRepository.getReferenceById(id);
		return userMapper.toDto(userEntity);
	}

	@Override
	public UsersResponseDto getClients(PaginationDto pagination) {

		PageRequest pageable = PaginationUtils.getPageable(pagination);
		Page<UserEntity> usersEntities = userRepository.findAll(pageable);

		UsersResponseDto users = new UsersResponseDto();
		users.setClients(usersEntities.toList());
		users.setTotals(usersEntities.getNumberOfElements());

		return users;

	}

	@Override
	public UserDto update(UserDto user) {

		UserEntity entity = userMapper.toEntity(user);
		return userMapper.toDto(userRepository.save(entity));
	}

}
