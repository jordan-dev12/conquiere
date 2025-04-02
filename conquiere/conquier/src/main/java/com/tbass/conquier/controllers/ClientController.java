package com.tbass.conquier.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.dtos.ClientsResponseDto;
import com.tbass.conquier.dtos.PaginationDto;
import com.tbass.conquier.service.ClientService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/user")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@PostMapping(value = "/save")
	public ResponseEntity<ClientDto> save(@RequestBody @Valid ClientDto request) {
		return ResponseEntity.ok(clientService.save(request));
	}

	@PostMapping(value = "/all")
	public ResponseEntity<ClientsResponseDto> getAll(@RequestBody @Valid PaginationDto pagination) {
		return ResponseEntity.ok(clientService.getClients(pagination));
	}

	@GetMapping(value = "/get/{id}")
	public ResponseEntity<ClientDto> getById(@PathVariable @Positive Long id) {
		return ResponseEntity.ok(clientService.getById(id));
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable @Positive Long id) {
		clientService.delete(id);
		return ResponseEntity.ok().build();
	}

}
