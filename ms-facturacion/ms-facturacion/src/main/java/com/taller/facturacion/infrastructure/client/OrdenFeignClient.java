package com.taller.facturacion.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-ordenes")
public interface OrdenFeignClient {

    @GetMapping("/api/orden/{id}")
    OrdenDto obtenerOrden(@PathVariable("id") Integer id);

    @GetMapping("/api/orden/{id}/inyectores")
    List<Map<String, Object>> obtenerInyectores(@PathVariable("id") Integer id);

    @GetMapping("/api/orden/{id}/servicios")
    List<Map<String, Object>> obtenerServicios(@PathVariable("id") Integer id);

    record OrdenDto(
            Integer idOrden,
            Integer idCliente,
            Integer idMecanico,
            String estado,
            Double total
    ) {}
}