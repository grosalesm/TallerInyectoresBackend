package com.taller.facturacion.application.port.outservice;

import com.taller.facturacion.domain.bean.Recibo;

import java.util.List;
import java.util.Optional;

public interface ReciboOutService {
    List<Recibo> listar();
    List<Recibo> listarPorMes(int mes, int anio);
    Optional<Recibo> obtenerPorId(Integer id);
    Optional<Recibo> obtenerPorOrden(Integer idOrden);
    Recibo insertar(Recibo recibo);
    Long contarRecibos();
}