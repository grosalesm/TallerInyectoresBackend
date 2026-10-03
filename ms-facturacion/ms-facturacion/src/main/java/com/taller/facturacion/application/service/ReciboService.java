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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReciboService implements ReciboUseCase {

    private final ReciboOutService reciboOutService;
    private final OrdenOutService ordenOutService;
    private final ReciboEventOutService reciboEventOutService;
    private final ReciboConstraints reciboConstraints;

    @Override
    public Flux<Recibo> listar() {
        return reciboOutService.listar();
    }

    @Override
    public Flux<Recibo> listarPorMes(int mes, int anio) {
        return reciboOutService.listarPorMes(mes, anio);
    }

    @Override
    public Mono<Recibo> obtenerPorId(Integer id) {
        return reciboOutService.obtenerPorId(id);
    }

    @Override
    public Mono<Recibo> obtenerPorOrden(Integer idOrden) {
        return reciboOutService.obtenerPorOrden(idOrden);
    }

    @Override
    public Mono<Recibo> registrar(Integer idOrden, String metodoPago, String numOperacion) {
        if (!reciboConstraints.validarMetodoPago(metodoPago)) {
            return Mono.error(new IllegalArgumentException(
                    "Método de pago no válido. Opciones: Efectivo, Yape, Plin, Transferencia."));
        }

        return ordenOutService.obtenerOrden(idOrden)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Orden no encontrada.")))
                .flatMap(orden -> validarYRegistrar(orden, metodoPago, numOperacion));
    }

    private Mono<Recibo> validarYRegistrar(OrdenInfo orden, String metodoPago, String numOperacion) {
        if ("Pagado".equalsIgnoreCase(orden.getEstado())) {
            return Mono.error(new IllegalArgumentException("Esta orden ya tiene un recibo registrado."));
        }
        if (!"Terminado".equalsIgnoreCase(orden.getEstado())) {
            return Mono.error(new IllegalArgumentException(
                    "Solo se puede registrar pago de órdenes con estado 'Terminado'."));
        }
        if (!reciboConstraints.validarMonto(orden.getTotal())) {
            return Mono.error(new IllegalArgumentException("El total de la orden no es válido."));
        }

        return reciboOutService.contarRecibos()
                .flatMap(total -> {
                    String numeroRecibo = String.format("REC-%04d", total + 1);
                    Recibo recibo = new Recibo();
                    recibo.setIdOrden(orden.getIdOrden());
                    recibo.setFechaPago(LocalDateTime.now());
                    recibo.setMonto(orden.getTotal());
                    recibo.setMetodoPago(metodoPago);
                    recibo.setNumOperacion(numOperacion != null ? numOperacion : "");
                    recibo.setNumeroRecibo(numeroRecibo);
                    return reciboOutService.insertar(recibo);
                })
                .doOnNext(reciboEventOutService::publicarPagoRegistrado);
    }
}