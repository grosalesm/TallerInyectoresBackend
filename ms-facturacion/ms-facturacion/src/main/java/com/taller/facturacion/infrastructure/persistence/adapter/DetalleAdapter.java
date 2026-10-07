package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.DetalleOutService;
import com.taller.facturacion.infrastructure.client.OrdenFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DetalleAdapter implements DetalleOutService {

    private final OrdenFeignClient ordenFeignClient;

    @Override
    @CircuitBreaker(name = "ms-ordenes", fallbackMethod = "obtenerInyectoresFallback")
    public List<Map<String, Object>> obtenerInyectoresPorOrden(Integer idOrden) {
        return ordenFeignClient.obtenerInyectores(idOrden);
    }

    public List<Map<String, Object>> obtenerInyectoresFallback(Integer idOrden, Throwable t) {
        return List.of();
    }

    @Override
    @CircuitBreaker(name = "ms-ordenes", fallbackMethod = "obtenerServiciosFallback")
    public List<Map<String, Object>> obtenerServiciosPorOrden(Integer idOrden) {
        return ordenFeignClient.obtenerServicios(idOrden);
    }

    public List<Map<String, Object>> obtenerServiciosFallback(Integer idOrden, Throwable t) {
        return List.of();
    }
}