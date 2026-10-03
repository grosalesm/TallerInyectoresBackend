package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.ClienteOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClienteAdapter implements ClienteOutService {

    private final WebClient.Builder webClientBuilder;

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

    @Override
    public Mono<String> obtenerDniCliente(Integer idCliente) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-clientes/api/cliente/{id}", idCliente)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .map(c -> c.dni() != null ? c.dni() : "")
                .onErrorReturn("");
    }

    public record ClienteDto(Integer idCliente, String nombres, String apellidos, String dni) {}
}