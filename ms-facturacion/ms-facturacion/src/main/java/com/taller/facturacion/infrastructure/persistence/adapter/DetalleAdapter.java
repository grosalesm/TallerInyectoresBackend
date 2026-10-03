package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.DetalleOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DetalleAdapter implements DetalleOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<List<Map<String, Object>>> obtenerInyectoresPorOrden(Integer idOrden) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-ordenes/api/orden/{id}/inyectores", idOrden)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .onErrorReturn(List.of());
    }

    @Override
    public Mono<List<Map<String, Object>>> obtenerServiciosPorOrden(Integer idOrden) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-ordenes/api/orden/{id}/servicios", idOrden)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .onErrorReturn(List.of());
    }
}