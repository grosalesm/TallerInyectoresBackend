package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.DetalleInyector;

import java.util.List;
import java.util.Optional;

public interface DetalleInyectorOutService {
    List<DetalleInyector> listarPorOrden(Integer idOrden);
    DetalleInyector guardar(DetalleInyector detalle);
    void eliminar(Integer idDetalle);
    Optional<DetalleInyector> obtenerPorId(Integer idDetalle);
}