package com.taller.ordenes.application.port.outservice;

public interface ClienteOutService {
    boolean existeCliente(Integer idCliente);
    String obtenerNombreCliente(Integer idCliente);
}