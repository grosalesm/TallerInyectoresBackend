package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.InyectorOutService;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.infrastructure.mapper.InyectorMapper;
import com.taller.catalogos.infrastructure.persistence.repository.InyectorR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InyectorAdapter implements InyectorOutService {

    private final InyectorR2dbcRepository repository;
    private final InyectorMapper mapper;

    @Override
    public Flux<Inyector> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Inyector> listarActivos() {
        return repository.findActivos().map(mapper::toDomain);
    }

    @Override
    public Mono<Inyector> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Inyector> insertar(Inyector inyector) {
        inyector.setIdInyector(null);
        return repository.save(mapper.toEntity(inyector)).map(mapper::toDomain);
    }

    @Override
    public Mono<Inyector> actualizar(Inyector inyector) {
        return repository.findById(inyector.getIdInyector())
                .flatMap(entity -> {
                    entity.setModelo(inyector.getModelo());
                    entity.setMarca(inyector.getMarca());
                    entity.setDescripcion(inyector.getDescripcion());
                    entity.setActivo(inyector.getActivo());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return repository.deleteById(id);
    }
}