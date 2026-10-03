package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.MecanicoOutService;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.infrastructure.mapper.MecanicoMapper;
import com.taller.catalogos.infrastructure.persistence.repository.MecanicoR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MecanicoAdapter implements MecanicoOutService {

    private final MecanicoR2dbcRepository repository;
    private final MecanicoMapper mapper;

    @Override
    public Flux<Mecanico> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Mecanico> listarActivos() {
        return repository.findActivos().map(mapper::toDomain);
    }

    @Override
    public Mono<Mecanico> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Mecanico> insertar(Mecanico mecanico) {
        mecanico.setIdMecanico(null);
        return repository.save(mapper.toEntity(mecanico)).map(mapper::toDomain);
    }

    @Override
    public Mono<Mecanico> actualizar(Mecanico mecanico) {
        return repository.findById(mecanico.getIdMecanico())
                .flatMap(entity -> {
                    entity.setNombres(mecanico.getNombres());
                    entity.setApellidos(mecanico.getApellidos());
                    entity.setEspecialidad(mecanico.getEspecialidad());
                    entity.setTelefono(mecanico.getTelefono());
                    entity.setActivo(mecanico.getActivo());
                    return repository.save(entity);
                })
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return repository.deleteById(id);
    }
}