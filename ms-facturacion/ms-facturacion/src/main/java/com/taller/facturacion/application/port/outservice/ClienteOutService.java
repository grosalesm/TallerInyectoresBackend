package com.taller.facturacion.application.port.outservice;

public interface ClienteOutService {
    String obtenerNombreCliente(Integer idCliente);
    String obtenerDniCliente(Integer idCliente);
}