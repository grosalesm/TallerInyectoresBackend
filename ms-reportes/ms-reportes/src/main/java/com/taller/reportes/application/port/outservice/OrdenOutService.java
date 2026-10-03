package com.taller.reportes.application.port.outservice;

import com.taller.reportes.domain.bean.OrdenResumen;
import reactor.core.publisher.Flux;

public interface OrdenOutService {
    Flux<OrdenResumen> listarTodas();
    Flux<OrdenResumen> listarPorEstado(String estado);
}