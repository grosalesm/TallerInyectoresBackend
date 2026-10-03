package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.MecanicoOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MecanicoAdapter implements MecanicoOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> obtenerNombreMecanico(Integer idMecanico) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/mecanico/{id}", idMecanico)
                .retrieve()
                .bodyToMono(MecanicoDto.class)
                .map(m -> (m.nombres() + " " + m.apellidos()).trim())
                .onErrorReturn("");
    }

    public record MecanicoDto(Integer idMecanico, String nombres, String apellidos) {}
}