package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.ReciboOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReciboAdapter implements ReciboOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Flux<Map<String, Object>> listarPorMes(int mes, int anio) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-facturacion/api/recibo/mes/{mes}/{anio}", mes, anio)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> Flux.empty());
    }
}