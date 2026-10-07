package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.ReciboOutService;
import com.taller.reportes.infrastructure.client.ReciboFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReciboAdapter implements ReciboOutService {

    private final ReciboFeignClient reciboFeignClient;

    @Override
    @CircuitBreaker(name = "ms-facturacion", fallbackMethod = "listarPorMesFallback")
    public List<Map<String, Object>> listarPorMes(int mes, int anio) {
        return reciboFeignClient.listarPorMes(mes, anio);
    }

    public List<Map<String, Object>> listarPorMesFallback(int mes, int anio, Throwable t) {
        return List.of();
    }
}