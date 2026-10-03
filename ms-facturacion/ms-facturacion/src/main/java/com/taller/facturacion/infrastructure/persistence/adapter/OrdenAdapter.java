package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.OrdenOutService;
import com.taller.facturacion.domain.bean.OrdenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<OrdenInfo> obtenerOrden(Integer idOrden) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-ordenes/api/orden/{id}", idOrden)
                .retrieve()
                .bodyToMono(OrdenResponse.class)
                .map(r -> new OrdenInfo(
                        r.idOrden(),
                        r.idCliente(),
                        r.idMecanico(),
                        r.estado(),
                        r.total()
                ))
                .onErrorResume(e -> Mono.empty());
    }

    public record OrdenResponse(
            Integer idOrden,
            Integer idCliente,
            Integer idMecanico,
            String estado,
            Double total
    ) {}
}