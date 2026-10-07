package com.taller.reportes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-ordenes")
public interface OrdenFeignClient {

    @GetMapping("/api/orden")
    List<Map<String, Object>> listarTodas();

    @GetMapping("/api/orden/estado/{estado}")
    List<Map<String, Object>> listarPorEstado(@PathVariable("estado") String estado);
}