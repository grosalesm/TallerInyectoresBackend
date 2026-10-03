package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.ClienteOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Flux<Map<String, Object>> listarTodos() {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-clientes/api/cliente")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(e -> Flux.empty());
    }
}