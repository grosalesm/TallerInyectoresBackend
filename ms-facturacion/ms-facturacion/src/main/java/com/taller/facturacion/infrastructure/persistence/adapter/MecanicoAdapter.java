package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.MecanicoOutService;
import com.taller.facturacion.infrastructure.client.MecanicoFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MecanicoAdapter implements MecanicoOutService {

    private final MecanicoFeignClient mecanicoFeignClient;

    @Override
    @CircuitBreaker(name = "ms-catalogos", fallbackMethod = "obtenerNombreMecanicoFallback")
    public String obtenerNombreMecanico(Integer idMecanico) {
        MecanicoFeignClient.MecanicoDto dto = mecanicoFeignClient.obtenerMecanico(idMecanico);
        if (dto == null) return "";
        String nombres = dto.nombres() != null ? dto.nombres() : "";
        String apellidos = dto.apellidos() != null ? dto.apellidos() : "";
        return (nombres + " " + apellidos).trim();
    }

    public String obtenerNombreMecanicoFallback(Integer idMecanico, Throwable t) {
        return "";
    }
}