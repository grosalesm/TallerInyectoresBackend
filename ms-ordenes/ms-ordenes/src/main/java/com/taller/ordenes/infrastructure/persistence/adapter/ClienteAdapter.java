package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.ClienteOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Boolean> existeCliente(Integer idCliente) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-clientes/api/cliente/{id}", idCliente)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .map(c -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<String> obtenerNombreCliente(Integer idCliente) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-clientes/api/cliente/{id}", idCliente)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .map(c -> (c.nombres() + " " + c.apellidos()).trim())
                .onErrorReturn("");
    }

    public record ClienteDto(Integer idCliente, String nombres, String apellidos, String dni) {}
}