package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.DetalleServicio;

import java.util.List;
import java.util.Optional;

public interface DetalleServicioOutService {
    List<DetalleServicio> listarPorOrden(Integer idOrden);
    DetalleServicio guardar(DetalleServicio detalle);
    void eliminar(Integer idDetalle);
    Optional<DetalleServicio> obtenerPorId(Integer idDetalle);
}