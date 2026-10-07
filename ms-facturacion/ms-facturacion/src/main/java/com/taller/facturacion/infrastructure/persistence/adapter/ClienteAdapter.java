package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.ClienteOutService;
import com.taller.facturacion.infrastructure.client.ClienteFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final ClienteFeignClient clienteFeignClient;

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

    @Override
    @CircuitBreaker(name = "ms-clientes", fallbackMethod = "obtenerDniClienteFallback")
    public String obtenerDniCliente(Integer idCliente) {
        ClienteFeignClient.ClienteDto dto = clienteFeignClient.obtenerCliente(idCliente);
        return dto != null && dto.dni() != null ? dto.dni() : "";
    }

    public String obtenerDniClienteFallback(Integer idCliente, Throwable t) {
        return "";
    }
}