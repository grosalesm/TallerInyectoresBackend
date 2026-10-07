package com.taller.facturacion.application.port.usecase;

import com.taller.facturacion.domain.bean.Recibo;

import java.util.List;
import java.util.Optional;

public interface ReciboUseCase {
    List<Recibo> listar();
    List<Recibo> listarPorMes(int mes, int anio);
    Optional<Recibo> obtenerPorId(Integer id);
    Optional<Recibo> obtenerPorOrden(Integer idOrden);
    Recibo registrar(Integer idOrden, String metodoPago, String numOperacion);
}