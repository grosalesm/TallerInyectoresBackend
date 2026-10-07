package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.MecanicoOutService;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.infrastructure.mapper.MecanicoMapper;
import com.taller.catalogos.infrastructure.persistence.entity.MecanicoEntity;
import com.taller.catalogos.infrastructure.persistence.repository.MecanicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MecanicoAdapter implements MecanicoOutService {

    private final MecanicoRepository repository;
    private final MecanicoMapper mapper;

    @Override
    public List<Mecanico> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Mecanico> listarActivos() {
        return repository.findByActivoTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Mecanico> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mecanico insertar(Mecanico mecanico) {
        mecanico.setIdMecanico(null);
        MecanicoEntity saved = repository.save(mapper.toEntity(mecanico));
        return mapper.toDomain(saved);
    }

    @Override
    public Mecanico actualizar(Mecanico mecanico) {
        MecanicoEntity entity = repository.findById(mecanico.getIdMecanico())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Mecánico no encontrado con id: " + mecanico.getIdMecanico()));
        entity.setNombres(mecanico.getNombres());
        entity.setApellidos(mecanico.getApellidos());
        entity.setEspecialidad(mecanico.getEspecialidad());
        entity.setTelefono(mecanico.getTelefono());
        entity.setActivo(mecanico.getActivo());
        MecanicoEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}