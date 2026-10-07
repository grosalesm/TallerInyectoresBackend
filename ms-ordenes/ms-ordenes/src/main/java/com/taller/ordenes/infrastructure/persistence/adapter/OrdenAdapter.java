package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.infrastructure.mapper.OrdenMapper;
import com.taller.ordenes.infrastructure.persistence.entity.OrdenEntity;
import com.taller.ordenes.infrastructure.persistence.repository.DetalleServicioRepository;
import com.taller.ordenes.infrastructure.persistence.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final OrdenRepository repository;
    private final DetalleServicioRepository detalleServicioRepository;
    private final OrdenMapper mapper;

    @Override
    public List<Orden> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Orden> listarPorEstado(String estado) {
        return repository.findByEstadoOrderByFechaIngresoDesc(estado).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Orden> obtenerPorId(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Orden insertar(Orden orden) {
        orden.setIdOrden(null);
        orden.setFechaIngreso(LocalDateTime.now());
        orden.setTotal(0.0);
        return mapper.toDomain(repository.save(mapper.toEntity(orden)));
    }

    @Override
    public void cambiarEstado(Integer idOrden, String estado) {
        OrdenEntity entity = repository.findById(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + idOrden));
        entity.setEstado(estado);
        if ("Pagado".equalsIgnoreCase(estado)) {
            entity.setFechaSalida(LocalDateTime.now());
        }
        repository.save(entity);
    }

    @Override
    public void actualizarTotal(Integer idOrden, Double total) {
        OrdenEntity entity = repository.findById(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada: " + idOrden));
        entity.setTotal(total);
        repository.save(entity);
    }

    @Override
    public Double calcularTotalPorOrden(Integer idOrden) {
        Double total = detalleServicioRepository.calcularTotalPorOrden(idOrden);
        return total != null ? total : 0.0;
    }
}