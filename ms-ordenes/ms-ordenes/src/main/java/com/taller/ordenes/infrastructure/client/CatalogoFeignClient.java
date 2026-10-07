package com.taller.ordenes.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogos")
public interface CatalogoFeignClient {

    @GetMapping("/api/inyector/{id}")
    InyectorDto obtenerInyector(@PathVariable("id") Integer id);

    @GetMapping("/api/servicio/{id}")
    ServicioDto obtenerServicio(@PathVariable("id") Integer id);

    @GetMapping("/api/mecanico/{id}")
    MecanicoDto obtenerMecanico(@PathVariable("id") Integer id);

    record InyectorDto(Integer idInyector, String modelo, String marca, String descripcion, Boolean activo) {}
    record ServicioDto(Integer idServicio, String nombre, String descripcion, Double precioBase, Boolean activo) {}
    record MecanicoDto(Integer idMecanico, String nombres, String apellidos, String especialidad, String telefono, Boolean activo) {}
}