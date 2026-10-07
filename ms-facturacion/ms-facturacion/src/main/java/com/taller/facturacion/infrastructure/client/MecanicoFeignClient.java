package com.taller.facturacion.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogos")
public interface MecanicoFeignClient {

    @GetMapping("/api/mecanico/{id}")
    MecanicoDto obtenerMecanico(@PathVariable("id") Integer id);

    record MecanicoDto(Integer idMecanico, String nombres, String apellidos, String especialidad,
                       String telefono, Boolean activo) {}
}