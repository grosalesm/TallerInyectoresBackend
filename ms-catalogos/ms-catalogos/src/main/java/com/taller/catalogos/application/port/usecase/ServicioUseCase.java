package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioUseCase {
    List<Servicio> listar();
    List<Servicio> listarActivos();
    Optional<Servicio> obtenerPorId(Integer id);
    Servicio guardar(Servicio servicio);
    void eliminar(Integer id);
}