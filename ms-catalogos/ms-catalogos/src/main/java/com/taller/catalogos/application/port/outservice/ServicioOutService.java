package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioOutService {
    List<Servicio> listar();
    List<Servicio> listarActivos();
    Optional<Servicio> obtenerPorId(Integer id);
    Servicio insertar(Servicio servicio);
    Servicio actualizar(Servicio servicio);
    void eliminar(Integer id);
}