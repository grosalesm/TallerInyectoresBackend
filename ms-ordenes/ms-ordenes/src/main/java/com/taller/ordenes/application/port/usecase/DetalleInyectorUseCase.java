package com.taller.ordenes.application.port.usecase;

import com.taller.ordenes.domain.bean.DetalleInyector;

import java.util.List;

public interface DetalleInyectorUseCase {
    List<DetalleInyector> listarPorOrden(Integer idOrden);
    void agregar(Integer idOrden, DetalleInyector detalle);
    void eliminar(Integer idDetalle);
}