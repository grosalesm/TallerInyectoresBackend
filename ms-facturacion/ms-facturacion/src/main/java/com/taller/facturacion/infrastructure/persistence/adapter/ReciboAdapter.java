package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.ReciboOutService;
import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.infrastructure.mapper.ReciboMapper;
import com.taller.facturacion.infrastructure.persistence.repository.ReciboR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReciboAdapter implements ReciboOutService {

    private final ReciboR2dbcRepository repository;
    private final ReciboMapper mapper;

    @Override
    public Flux<Recibo> listar() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Recibo> listarPorMes(int mes, int anio) {
        return repository.findByMesAndAnio(mes, anio).map(mapper::toDomain);
    }

    @Override
    public Mono<Recibo> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Recibo> obtenerPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).map(mapper::toDomain);
    }

    @Override
    public Mono<Recibo> insertar(Recibo recibo) {
        recibo.setIdRecibo(null);
        return repository.save(mapper.toEntity(recibo)).map(mapper::toDomain);
    }

    @Override
    public Mono<Long> contarRecibos() {
        return repository.contarRecibos().defaultIfEmpty(0L);
    }
}