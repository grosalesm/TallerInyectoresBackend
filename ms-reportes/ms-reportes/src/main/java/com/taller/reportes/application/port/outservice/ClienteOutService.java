package com.taller.reportes.application.port.outservice;

import reactor.core.publisher.Flux;

import java.util.Map;

public interface ClienteOutService {
    Flux<Map<String, Object>> listarTodos();
}