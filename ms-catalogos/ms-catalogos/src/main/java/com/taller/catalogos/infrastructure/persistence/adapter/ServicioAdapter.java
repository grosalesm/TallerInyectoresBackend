package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.ServicioOutService;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.mapper.ServicioMapper;
import com.taller.catalogos.infrastructure.persistence.repository.ServicioR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ServicioAdapter implements ServicioOutService {

    private final ServicioR2dbcRepository repository;
    private final ServicioMapper mapper;

    @Override
    public Flux<Servicio> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Servicio> listarActivos() {
        return repository.findActivos().map(mapper::toDomain);
    }

    @Override
    public Mono<Servicio> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Servicio> insertar(Servicio servicio) {
        servicio.setIdServicio(null);
        return repository.save(mapper.toEntity(servicio)).map(mapper::toDomain);
    }

    @Override
    public Mono<Servicio> actualizar(Servicio servicio) {
        return repository.findById(servicio.getIdServicio())
                .flatMap(entity -> {
                    entity.setNombre(servicio.getNombre());
                    entity.setDescripcion(servicio.getDescripcion());
                    entity.setPrecioBase(servicio.getPrecioBase());
                    entity.setActivo(servicio.getActivo());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return repository.deleteById(id);
    }
}