package com.taller.facturacion.application.port.outservice;

import com.taller.facturacion.domain.bean.Recibo;

public interface ReciboEventOutService {
    void publicarPagoRegistrado(Recibo recibo);
}