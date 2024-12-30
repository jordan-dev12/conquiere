package com.tbass.conquier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbass.conquier.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
