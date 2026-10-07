package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.ReciboOutService;
import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.infrastructure.mapper.ReciboMapper;
import com.taller.facturacion.infrastructure.persistence.repository.ReciboRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReciboAdapter implements ReciboOutService {

    private final ReciboRepository repository;
    private final ReciboMapper mapper;

    @Override
    public List<Recibo> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Recibo> listarPorMes(int mes, int anio) {
        return repository.findByMesAndAnio(mes, anio).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Recibo> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Recibo> obtenerPorOrden(Integer idOrden) {
        return repository.findByIdOrden(idOrden).map(mapper::toDomain);
    }

    @Override
    public Recibo insertar(Recibo recibo) {
        recibo.setIdRecibo(null);
        return mapper.toDomain(repository.save(mapper.toEntity(recibo)));
    }

    @Override
    public Long contarRecibos() {
        return repository.count();
    }
}