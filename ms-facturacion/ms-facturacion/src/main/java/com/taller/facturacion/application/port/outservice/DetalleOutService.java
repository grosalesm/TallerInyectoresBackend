package com.taller.facturacion.application.port.outservice;

import java.util.List;
import java.util.Map;

public interface DetalleOutService {
    List<Map<String, Object>> obtenerInyectoresPorOrden(Integer idOrden);
    List<Map<String, Object>> obtenerServiciosPorOrden(Integer idOrden);
}