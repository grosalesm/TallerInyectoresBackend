package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.ClienteOutService;
import com.taller.ordenes.infrastructure.client.ClienteFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final ClienteFeignClient clienteFeignClient;

    @Override
    @CircuitBreaker(name = "ms-clientes", fallbackMethod = "existeClienteFallback")
    public boolean existeCliente(Integer idCliente) {
        return clienteFeignClient.obtenerCliente(idCliente) != null;
    }

    public boolean existeClienteFallback(Integer idCliente, Throwable t) {
        return false;
    }

    @Override
    @CircuitBreaker(name = "ms-clientes", fallbackMethod = "obtenerNombreClienteFallback")
    public String obtenerNombreCliente(Integer idCliente) {
        ClienteFeignClient.ClienteDto dto = clienteFeignClient.obtenerCliente(idCliente);
        if (dto == null) return "";
        String nombres = dto.nombres() != null ? dto.nombres() : "";
        String apellidos = dto.apellidos() != null ? dto.apellidos() : "";
        return (nombres + " " + apellidos).trim();
    }

    public String obtenerNombreClienteFallback(Integer idCliente, Throwable t) {
        return "";
    }
}