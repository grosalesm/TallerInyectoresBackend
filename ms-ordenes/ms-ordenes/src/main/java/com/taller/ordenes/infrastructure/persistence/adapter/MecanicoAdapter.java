package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.MecanicoOutService;
import com.taller.ordenes.infrastructure.client.CatalogoFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MecanicoAdapter implements MecanicoOutService {

    private final CatalogoFeignClient catalogoFeignClient;

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "existeMecanicoFallback")
    public boolean existeMecanico(Integer idMecanico) {
        return catalogoFeignClient.obtenerMecanico(idMecanico) != null;
    }

    public boolean existeMecanicoFallback(Integer idMecanico, Throwable t) {
        return false;
    }

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerNombreMecanicoFallback")
    public String obtenerNombreMecanico(Integer idMecanico) {
        CatalogoFeignClient.MecanicoDto dto = catalogoFeignClient.obtenerMecanico(idMecanico);
        if (dto == null) return "";
        String nombres = dto.nombres() != null ? dto.nombres() : "";
        String apellidos = dto.apellidos() != null ? dto.apellidos() : "";
        return (nombres + " " + apellidos).trim();
    }

    public String obtenerNombreMecanicoFallback(Integer idMecanico, Throwable t) {
        return "";
    }
}