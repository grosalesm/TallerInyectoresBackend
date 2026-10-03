package com.taller.clientes.infrastructure.persistence.adapter;

import com.taller.clientes.application.port.outservice.ClienteOutService;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.mapper.ClienteMapper;
import com.taller.clientes.infrastructure.persistence.repository.ClienteR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final ClienteR2dbcRepository repository;
    private final ClienteMapper mapper;

    @Override
    public Flux<Cliente> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Cliente> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Cliente> obtenerPorDni(String dni) {
        return repository.findByDni(dni).map(mapper::toDomain);
    }

    @Override
    public Mono<Cliente> insertar(Cliente cliente) {
        cliente.setIdCliente(null);
        cliente.setFechaRegistro(LocalDateTime.now());
        return repository.save(mapper.toEntity(cliente)).map(mapper::toDomain);
    }

    @Override
    public Mono<Cliente> actualizar(Cliente cliente) {
        return repository.findById(cliente.getIdCliente())
                .flatMap(entity -> {
                    entity.setNombres(cliente.getNombres());
                    entity.setApellidos(cliente.getApellidos());
                    entity.setDni(cliente.getDni());
                    entity.setTelefono(cliente.getTelefono());
                    entity.setEmail(cliente.getEmail());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return repository.deleteById(id);
    }
}