package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import com.taller.ordenes.infrastructure.client.CatalogoFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogoAdapter implements CatalogoOutService {

    private final CatalogoFeignClient catalogoFeignClient;

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "existeInyectorFallback")
    public boolean existeInyector(Integer idInyector) {
        return catalogoFeignClient.obtenerInyector(idInyector) != null;
    }

    public boolean existeInyectorFallback(Integer idInyector, Throwable t) {
        return false;
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "existeServicioFallback")
    public boolean existeServicio(Integer idServicio) {
        return catalogoFeignClient.obtenerServicio(idServicio) != null;
    }

    public boolean existeServicioFallback(Integer idServicio, Throwable t) {
        return false;
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerModeloInyectorFallback")
    public String obtenerModeloInyector(Integer idInyector) {
        CatalogoFeignClient.InyectorDto dto = catalogoFeignClient.obtenerInyector(idInyector);
        return dto != null && dto.modelo() != null ? dto.modelo() : "";
    }

    public String obtenerModeloInyectorFallback(Integer idInyector, Throwable t) {
        return "";
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerMarcaInyectorFallback")
    public String obtenerMarcaInyector(Integer idInyector) {
        CatalogoFeignClient.InyectorDto dto = catalogoFeignClient.obtenerInyector(idInyector);
        return dto != null && dto.marca() != null ? dto.marca() : "";
    }

    public String obtenerMarcaInyectorFallback(Integer idInyector, Throwable t) {
        return "";
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerNombreServicioFallback")
    public String obtenerNombreServicio(Integer idServicio) {
        CatalogoFeignClient.ServicioDto dto = catalogoFeignClient.obtenerServicio(idServicio);
        return dto != null && dto.nombre() != null ? dto.nombre() : "";
    }

    public String obtenerNombreServicioFallback(Integer idServicio, Throwable t) {
        return "";
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerPrecioServicioFallback")
    public Double obtenerPrecioServicio(Integer idServicio) {
        CatalogoFeignClient.ServicioDto dto = catalogoFeignClient.obtenerServicio(idServicio);
        return dto != null && dto.precioBase() != null ? dto.precioBase() : 0.0;
    }

    public Double obtenerPrecioServicioFallback(Integer idServicio, Throwable t) {
        return 0.0;
    }
}