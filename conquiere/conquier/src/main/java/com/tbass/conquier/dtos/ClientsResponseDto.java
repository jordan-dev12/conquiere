package com.tbass.conquier.dtos;

import java.util.List;

import com.tbass.conquier.entity.Client;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientsResponseDto {

	List<Client> clients;
	int totals;
	
}
