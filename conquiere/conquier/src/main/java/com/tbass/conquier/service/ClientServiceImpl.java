package com.tbass.conquier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.dtos.ClientsResponseDto;
import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.entity.Client;
import com.tbass.conquier.mappers.ClientMapper;
import com.tbass.conquier.repositories.ClientRepository;
import com.tbass.conquier.utility.PaginationUtils;

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
	public void delete(long id) {
		clientRepository.deleteById(id);
	}

	@Override
	public ClientDto getById(long id) {
		 Client clientEntity = clientRepository.getReferenceById(id);
		return clientMapper.toDto(clientEntity);
	}


	@Override
	public ClientsResponseDto getClients(PaginationDto pagination) {

		  PageRequest pageable = PaginationUtils.getPageable(pagination);
          Page<Client> entities = clientRepository.findAll(pageable);

          ClientsResponseDto clients = new ClientsResponseDto();
			if (entities != null) {
				clients.setClients(entities.toList());
				clients.setTotals(entities.getNumber());

			}
          return clients;

	}

	@Override
	public ClientDto update(ClientDto client) {

		Client entity = clientMapper.toEntity(client);
		return clientMapper.toDto(clientRepository.save(entity));
	}



}
