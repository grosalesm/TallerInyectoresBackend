package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.OrdenOutService;
import com.taller.reportes.domain.bean.OrdenResumen;
import com.taller.reportes.infrastructure.client.OrdenFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final OrdenFeignClient ordenFeignClient;

    @Override
    @CircuitBreaker(name = "ms-ordenes", fallbackMethod = "listarTodasFallback")
    public List<OrdenResumen> listarTodas() {
        return ordenFeignClient.listarTodas().stream()
                .map(this::mapToOrdenResumen)
                .toList();
    }

    public List<OrdenResumen> listarTodasFallback(Throwable t) {
        return List.of();
    }

    @Override
    @CircuitBreaker(name = "ms-ordenes", fallbackMethod = "listarPorEstadoFallback")
    public List<OrdenResumen> listarPorEstado(String estado) {
        return ordenFeignClient.listarPorEstado(estado).stream()
                .map(this::mapToOrdenResumen)
                .toList();
    }

    public List<OrdenResumen> listarPorEstadoFallback(String estado, Throwable t) {
        return List.of();
    }

    private OrdenResumen mapToOrdenResumen(Map<String, Object> map) {
        OrdenResumen orden = new OrdenResumen();
        orden.setIdOrden(map.get("idOrden") instanceof Number
                ? ((Number) map.get("idOrden")).intValue() : null);
        orden.setEstado(map.get("estado") != null ? map.get("estado").toString() : "");
        Object fecha = map.get("fechaIngreso");
        if (fecha != null) {
            try {
                orden.setFechaIngreso(LocalDateTime.parse(fecha.toString()));
            } catch (Exception e) {
                orden.setFechaIngreso(null);
            }
        }
        orden.setTotal(map.get("total") instanceof Number
                ? ((Number) map.get("total")).doubleValue() : 0.0);
        orden.setNombreCliente(map.get("nombreCliente") != null
                ? map.get("nombreCliente").toString() : "");
        orden.setNombreMecanico(map.get("nombreMecanico") != null
                ? map.get("nombreMecanico").toString() : "");
        return orden;
    }
}