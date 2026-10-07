package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.DetalleInyectorOutService;
import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.infrastructure.mapper.DetalleInyectorMapper;
import com.taller.ordenes.infrastructure.persistence.repository.DetalleInyectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DetalleInyectorAdapter implements DetalleInyectorOutService {

    private final DetalleInyectorRepository repository;
    private final DetalleInyectorMapper mapper;

    @Override
    public List<DetalleInyector> listarPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).stream().map(mapper::toDomain).toList();
    }

    @Override
    public DetalleInyector guardar(DetalleInyector detalle) {
        detalle.setIdDetalle(null);
        return mapper.toDomain(repository.save(mapper.toEntity(detalle)));
    }

    @Override
    public void eliminar(Integer idDetalle) {
        repository.deleteById(idDetalle);
    }

    @Override
    public Optional<DetalleInyector> obtenerPorId(Integer idDetalle) {
        return repository.findById(idDetalle).map(mapper::toDomain);
    }
}