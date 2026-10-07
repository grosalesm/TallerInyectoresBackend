package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.Orden;

import java.util.List;
import java.util.Optional;

public interface OrdenUseCase {
    List<Orden> listar();
    List<Orden> listarPorEstado(String estado);
    Optional<Orden> obtenerPorId(Integer id);
    Orden crear(Orden orden);
    void cambiarEstado(Integer idOrden, String estado);
    void cambiarEstadoPorEvento(Integer idOrden, String estado);
}