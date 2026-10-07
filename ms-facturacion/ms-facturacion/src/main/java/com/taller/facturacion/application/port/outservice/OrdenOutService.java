package com.taller.facturacion.application.port.outservice;

import com.taller.facturacion.domain.bean.OrdenInfo;

import java.util.Optional;

public interface OrdenOutService {
    Optional<OrdenInfo> obtenerOrden(Integer idOrden);
}