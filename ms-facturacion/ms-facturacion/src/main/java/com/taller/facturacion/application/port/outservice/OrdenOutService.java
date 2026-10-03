package com.taller.facturacion.application.port.outservice;

import com.taller.facturacion.domain.bean.OrdenInfo;
import reactor.core.publisher.Mono;

public interface OrdenOutService {
    Mono<OrdenInfo> obtenerOrden(Integer idOrden);
}