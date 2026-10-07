package com.taller.clientes.infrastructure.persistence.adapter;

import com.taller.clientes.application.port.outservice.ClienteOutService;
import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.mapper.ClienteMapper;
import com.taller.clientes.infrastructure.persistence.entity.ClienteEntity;
import com.taller.clientes.infrastructure.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Override
    public List<Cliente> listar() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Cliente> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> obtenerPorDni(String dni) {
        return repository.findByDni(dni).map(mapper::toDomain);
    }

    @Override
    public Cliente insertar(Cliente cliente) {
        cliente.setIdCliente(null);
        cliente.setFechaRegistro(LocalDateTime.now());
        ClienteEntity saved = repository.save(mapper.toEntity(cliente));
        return mapper.toDomain(saved);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        ClienteEntity entity = repository.findById(cliente.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente no encontrado con id: " + cliente.getIdCliente()));
        entity.setNombres(cliente.getNombres());
        entity.setApellidos(cliente.getApellidos());
        entity.setDni(cliente.getDni());
        entity.setTelefono(cliente.getTelefono());
        entity.setEmail(cliente.getEmail());
        ClienteEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}