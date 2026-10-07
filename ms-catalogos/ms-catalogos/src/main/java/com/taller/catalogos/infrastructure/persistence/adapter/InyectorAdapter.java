package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.InyectorOutService;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.infrastructure.mapper.InyectorMapper;
import com.taller.catalogos.infrastructure.persistence.entity.InyectorEntity;
import com.taller.catalogos.infrastructure.persistence.repository.InyectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InyectorAdapter implements InyectorOutService {

    private final InyectorRepository repository;
    private final InyectorMapper mapper;

    @Override
    public List<Inyector> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Inyector> listarActivos() {
        return repository.findByActivoTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Inyector> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Inyector insertar(Inyector inyector) {
        inyector.setIdInyector(null);
        InyectorEntity saved = repository.save(mapper.toEntity(inyector));
        return mapper.toDomain(saved);
    }

    @Override
    public Inyector actualizar(Inyector inyector) {
        InyectorEntity entity = repository.findById(inyector.getIdInyector())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Inyector no encontrado con id: " + inyector.getIdInyector()));
        entity.setModelo(inyector.getModelo());
        entity.setMarca(inyector.getMarca());
        entity.setDescripcion(inyector.getDescripcion());
        entity.setActivo(inyector.getActivo());
        InyectorEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}