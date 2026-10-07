package com.taller.clientes.infrastructure.persistence.repository;

import com.taller.clientes.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByDni(String dni);
}