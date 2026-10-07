package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.ServicioOutService;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.mapper.ServicioMapper;
import com.taller.catalogos.infrastructure.persistence.entity.ServicioEntity;
import com.taller.catalogos.infrastructure.persistence.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServicioAdapter implements ServicioOutService {

    private final ServicioRepository repository;
    private final ServicioMapper mapper;

    @Override
    public List<Servicio> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Servicio> listarActivos() {
        return repository.findByActivoTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Servicio> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Servicio insertar(Servicio servicio) {
        servicio.setIdServicio(null);
        ServicioEntity saved = repository.save(mapper.toEntity(servicio));
        return mapper.toDomain(saved);
    }

    @Override
    public Servicio actualizar(Servicio servicio) {
        ServicioEntity entity = repository.findById(servicio.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Servicio no encontrado con id: " + servicio.getIdServicio()));
        entity.setNombre(servicio.getNombre());
        entity.setDescripcion(servicio.getDescripcion());
        entity.setPrecioBase(servicio.getPrecioBase());
        entity.setActivo(servicio.getActivo());
        ServicioEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}