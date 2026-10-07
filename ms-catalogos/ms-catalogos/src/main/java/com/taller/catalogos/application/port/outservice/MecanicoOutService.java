package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Mecanico;

import java.util.List;
import java.util.Optional;

public interface MecanicoOutService {
    List<Mecanico> listar();
    List<Mecanico> listarActivos();
    Optional<Mecanico> obtenerPorId(Integer id);
    Mecanico insertar(Mecanico mecanico);
    Mecanico actualizar(Mecanico mecanico);
    void eliminar(Integer id);
}