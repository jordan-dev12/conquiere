package com.tbass.conquier.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbass.conquier.dtos.ClientDto;
import com.tbass.conquier.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(name = "/api")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@PostMapping("/save")
	public ResponseEntity<ClientDto> save(@RequestBody @Valid ClientDto request) {
		return ResponseEntity.ok(clientService.save(request));
	}

}
