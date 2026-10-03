package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CatalogoAdapter implements CatalogoOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Boolean> existeInyector(Integer idInyector) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/inyector/{id}", idInyector)
                .retrieve()
                .bodyToMono(InyectorDto.class)
                .map(i -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> existeServicio(Integer idServicio) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/servicio/{id}", idServicio)
                .retrieve()
                .bodyToMono(ServicioDto.class)
                .map(s -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<String> obtenerModeloInyector(Integer idInyector) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/inyector/{id}", idInyector)
                .retrieve()
                .bodyToMono(InyectorDto.class)
                .map(i -> i.modelo() != null ? i.modelo() : "")
                .onErrorReturn("");
    }

    @Override
    public Mono<String> obtenerMarcaInyector(Integer idInyector) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/inyector/{id}", idInyector)
                .retrieve()
                .bodyToMono(InyectorDto.class)
                .map(i -> i.marca() != null ? i.marca() : "")
                .onErrorReturn("");
    }

    @Override
    public Mono<String> obtenerNombreServicio(Integer idServicio) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/servicio/{id}", idServicio)
                .retrieve()
                .bodyToMono(ServicioDto.class)
                .map(s -> s.nombre() != null ? s.nombre() : "")
                .onErrorReturn("");
    }

    @Override
    public Mono<Double> obtenerPrecioServicio(Integer idServicio) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-catalogos/api/servicio/{id}", idServicio)
                .retrieve()
                .bodyToMono(ServicioDto.class)
                .map(s -> s.precioBase() != null ? s.precioBase() : 0.0)
                .onErrorReturn(0.0);
    }

    public record InyectorDto(Integer idInyector, String modelo, String marca, String descripcion, Boolean activo) {}
    public record ServicioDto(Integer idServicio, String nombre, String descripcion, Double precioBase, Boolean activo) {}
}