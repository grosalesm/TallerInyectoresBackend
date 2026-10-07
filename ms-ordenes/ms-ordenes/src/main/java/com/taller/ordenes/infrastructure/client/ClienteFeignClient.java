package com.taller.ordenes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-clientes")
public interface ClienteFeignClient {

    @GetMapping("/api/cliente/{id}")
    ClienteDto obtenerCliente(@PathVariable("id") Integer id);

    record ClienteDto(Integer idCliente, String nombres, String apellidos, String dni) {}
}