package com.taller.reportes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-facturacion")
public interface ReciboFeignClient {

    @GetMapping("/api/recibo/mes/{mes}/{anio}")
    List<Map<String, Object>> listarPorMes(@PathVariable("mes") int mes,
                                           @PathVariable("anio") int anio);
}