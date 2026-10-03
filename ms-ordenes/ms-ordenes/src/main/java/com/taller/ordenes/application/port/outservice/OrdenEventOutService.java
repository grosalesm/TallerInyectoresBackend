package com.taller.ordenes.application.port.outservice;

import com.taller.ordenes.domain.bean.Orden;

public interface OrdenEventOutService {
    void publicarOrdenCreada(Orden orden);
    void publicarEstadoCambiado(Integer idOrden, String estadoAnterior, String estadoNuevo);
}