package com.taller.reportes.application.port.outservice;

import com.taller.reportes.domain.bean.OrdenResumen;

import java.util.List;

public interface OrdenOutService {
    List<OrdenResumen> listarTodas();
    List<OrdenResumen> listarPorEstado(String estado);
}