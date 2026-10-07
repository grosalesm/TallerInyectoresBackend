package com.taller.reportes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-clientes")
public interface ClienteFeignClient {

    @GetMapping("/api/cliente")
    List<Map<String, Object>> listarTodos();
}