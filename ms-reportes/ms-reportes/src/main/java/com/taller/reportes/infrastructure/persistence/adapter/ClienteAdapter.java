package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.ClienteOutService;
import com.taller.reportes.infrastructure.client.ClienteFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final ClienteFeignClient clienteFeignClient;

    @Override
    @CircuitBreaker(name = "ms-clientes", fallbackMethod = "listarTodosFallback")
    public List<Map<String, Object>> listarTodos() {
        return clienteFeignClient.listarTodos();
    }

    public List<Map<String, Object>> listarTodosFallback(Throwable t) {
        return List.of();
    }
}