package com.taller.ordenes.application.port.outservice;

public interface MecanicoOutService {
    boolean existeMecanico(Integer idMecanico);
    String obtenerNombreMecanico(Integer idMecanico);
}