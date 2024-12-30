package com.tbass.conquier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.entity.Client;
import com.tbass.conquier.mappers.ClientMapper;
import com.tbass.conquier.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientMapper clientMapper;

	@Override
	public ClientDto save(ClientDto client) {

		Client entity = clientMapper.toEntity(client);
		return clientMapper.toDto(clientRepository.save(entity));
	}

	@Override
	public ClientDto update(ClientDto todo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ClientDto getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDto> getAllTodo() {
		// TODO Auto-generated method stub
		return null;
	}

}
