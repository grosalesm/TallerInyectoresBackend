package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.Orden;

import java.util.List;
import java.util.Optional;

public interface OrdenOutService {
    List<Orden> listar();
    List<Orden> listarPorEstado(String estado);
    Optional<Orden> obtenerPorId(Integer id);
    Orden insertar(Orden orden);
    void cambiarEstado(Integer idOrden, String estado);
    void actualizarTotal(Integer idOrden, Double total);
    Double calcularTotalPorOrden(Integer idOrden);
}