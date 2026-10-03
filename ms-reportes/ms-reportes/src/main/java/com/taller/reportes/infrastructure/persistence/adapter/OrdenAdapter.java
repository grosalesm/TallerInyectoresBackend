package com.taller.reportes.infrastructure.persistence.adapter;

import com.taller.reportes.application.port.outservice.OrdenOutService;
import com.taller.reportes.domain.bean.OrdenResumen;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Flux<OrdenResumen> listarTodas() {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-ordenes/api/orden")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(this::mapToOrdenResumen)
                .onErrorResume(e -> Flux.empty());
    }

    @Override
    public Flux<OrdenResumen> listarPorEstado(String estado) {
        return webClientBuilder.build()
                .get()
                .uri("lb://ms-ordenes/api/orden/estado/{estado}", estado)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(this::mapToOrdenResumen)
                .onErrorResume(e -> Flux.empty());
    }

    private OrdenResumen mapToOrdenResumen(Map<String, Object> map) {
        OrdenResumen orden = new OrdenResumen();
        orden.setIdOrden(map.get("idOrden") instanceof Number ? ((Number) map.get("idOrden")).intValue() : null);
        orden.setEstado(map.get("estado") != null ? map.get("estado").toString() : "");
        Object fecha = map.get("fechaIngreso");
        if (fecha != null) {
            try {
                orden.setFechaIngreso(LocalDateTime.parse(fecha.toString()));
            } catch (Exception e) {
                orden.setFechaIngreso(null);
            }
        }
        orden.setTotal(map.get("total") instanceof Number ? ((Number) map.get("total")).doubleValue() : 0.0);
        orden.setNombreCliente(map.get("nombreCliente") != null ? map.get("nombreCliente").toString() : "");
        orden.setNombreMecanico(map.get("nombreMecanico") != null ? map.get("nombreMecanico").toString() : "");
        return orden;
    }
}