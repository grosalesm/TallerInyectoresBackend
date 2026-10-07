package com.taller.ordenes.application.port.outservice;

public interface CatalogoOutService {
    boolean existeInyector(Integer idInyector);
    boolean existeServicio(Integer idServicio);
    String obtenerModeloInyector(Integer idInyector);
    String obtenerMarcaInyector(Integer idInyector);
    String obtenerNombreServicio(Integer idServicio);
    Double obtenerPrecioServicio(Integer idServicio);
}