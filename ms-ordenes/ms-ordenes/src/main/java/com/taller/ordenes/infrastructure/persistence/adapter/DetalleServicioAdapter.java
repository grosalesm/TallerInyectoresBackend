package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.DetalleServicioOutService;
import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.infrastructure.mapper.DetalleServicioMapper;
import com.taller.ordenes.infrastructure.persistence.repository.DetalleServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DetalleServicioAdapter implements DetalleServicioOutService {

    private final DetalleServicioRepository repository;
    private final DetalleServicioMapper mapper;

    @Override
    public List<DetalleServicio> listarPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).stream().map(mapper::toDomain).toList();
    }

    @Override
    public DetalleServicio guardar(DetalleServicio detalle) {
        detalle.setIdDetalle(null);
        return mapper.toDomain(repository.save(mapper.toEntity(detalle)));
    }

    @Override
    public void eliminar(Integer idDetalle) {
        repository.deleteById(idDetalle);
    }

    @Override
    public Optional<DetalleServicio> obtenerPorId(Integer idDetalle) {
        return repository.findById(idDetalle).map(mapper::toDomain);
    }
}