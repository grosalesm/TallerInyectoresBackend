package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.OrdenOutService;
import com.taller.facturacion.domain.bean.OrdenInfo;
import com.taller.facturacion.infrastructure.client.OrdenFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdenAdapter implements OrdenOutService {

    private final OrdenFeignClient ordenFeignClient;

    @Override
    @CircuitBreaker(name = "ms-ordenes", fallbackMethod = "obtenerOrdenFallback")
    public Optional<OrdenInfo> obtenerOrden(Integer idOrden) {
        OrdenFeignClient.OrdenDto dto = ordenFeignClient.obtenerOrden(idOrden);
        if (dto == null) return Optional.empty();
        return Optional.of(new OrdenInfo(
                dto.idOrden(),
                dto.idCliente(),
                dto.idMecanico(),
                dto.estado(),
                dto.total()
        ));
    }

    public Optional<OrdenInfo> obtenerOrdenFallback(Integer idOrden, Throwable t) {
        return Optional.empty();
    }
}