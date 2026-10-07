package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.DetalleServicio;

import java.util.List;

public interface DetalleServicioUseCase {
    List<DetalleServicio> listarPorOrden(Integer idOrden);
    void agregar(Integer idOrden, DetalleServicio detalle);
    void eliminar(Integer idDetalle);
}