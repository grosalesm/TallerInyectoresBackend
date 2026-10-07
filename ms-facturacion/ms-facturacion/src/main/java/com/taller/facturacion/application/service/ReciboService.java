package com.taller.facturacion.application.service;

import com.taller.facturacion.application.port.outservice.OrdenOutService;
import com.taller.facturacion.application.port.outservice.ReciboEventOutService;
import com.taller.facturacion.application.port.outservice.ReciboOutService;
import com.taller.facturacion.application.port.usecase.ReciboUseCase;
import com.taller.facturacion.domain.bean.OrdenInfo;
import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.domain.constraint.ReciboConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReciboService implements ReciboUseCase {

    private final ReciboOutService reciboOutService;
    private final OrdenOutService ordenOutService;
    private final ReciboEventOutService reciboEventOutService;
    private final ReciboConstraints reciboConstraints;

    @Override
    public List<Recibo> listar() {
        return reciboOutService.listar();
    }

    @Override
    public List<Recibo> listarPorMes(int mes, int anio) {
        return reciboOutService.listarPorMes(mes, anio);
    }

    @Override
    public Optional<Recibo> obtenerPorId(Integer id) {
        return reciboOutService.obtenerPorId(id);
    }

    @Override
    public Optional<Recibo> obtenerPorOrden(Integer idOrden) {
        return reciboOutService.obtenerPorOrden(idOrden);
    }

    @Override
    @Transactional
    public Recibo registrar(Integer idOrden, String metodoPago, String numOperacion) {
        if (!reciboConstraints.validarMetodoPago(metodoPago)) {
            throw new IllegalArgumentException(
                    "Método de pago no válido. Opciones: Efectivo, Yape, Plin, Transferencia.");
        }

        OrdenInfo orden = ordenOutService.obtenerOrden(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada."));

        if ("Pagado".equalsIgnoreCase(orden.getEstado())) {
            throw new IllegalArgumentException("Esta orden ya tiene un recibo registrado.");
        }
        if (!"Terminado".equalsIgnoreCase(orden.getEstado())) {
            throw new IllegalArgumentException(
                    "Solo se puede registrar pago de órdenes con estado 'Terminado'.");
        }
        if (!reciboConstraints.validarMonto(orden.getTotal())) {
            throw new IllegalArgumentException("El total de la orden no es válido.");
        }

        Long total = reciboOutService.contarRecibos();
        String numeroRecibo = String.format("REC-%04d", total + 1);

        Recibo recibo = new Recibo();
        recibo.setIdOrden(orden.getIdOrden());
        recibo.setFechaPago(LocalDateTime.now());
        recibo.setMonto(orden.getTotal());
        recibo.setMetodoPago(metodoPago);
        recibo.setNumOperacion(numOperacion != null ? numOperacion : "");
        recibo.setNumeroRecibo(numeroRecibo);

        Recibo guardado = reciboOutService.insertar(recibo);
        reciboEventOutService.publicarPagoRegistrado(guardado);
        return guardado;
    }
}