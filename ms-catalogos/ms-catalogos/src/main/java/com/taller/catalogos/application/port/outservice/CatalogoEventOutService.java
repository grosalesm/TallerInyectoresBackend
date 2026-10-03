package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.bean.Servicio;

public interface CatalogoEventOutService {
    void publicarInyectorActualizado(Inyector inyector);
    void publicarServicioActualizado(Servicio servicio);
    void publicarMecanicoActualizado(Mecanico mecanico);
}