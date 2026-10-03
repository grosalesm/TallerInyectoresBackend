package com.taller.ordenes.infrastructure.consumer;

import com.taller.ordenes.application.port.usecase.OrdenUseCase;
import com.taller.ordenes.domain.enums.EstadoOrden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReciboEventConsumer {

    private final OrdenUseCase ordenUseCase;

    @RabbitListener(queues = "ordenes.pago.registrado.queue")
    public void onPagoRegistrado(Map<String, Object> evento) {
        try {
            Object idOrdenObj = evento.get("idOrden");
            if (idOrdenObj == null) {
                log.warn("Evento pago.registrado sin idOrden: {}", evento);
                return;
            }
            Integer idOrden = ((Number) idOrdenObj).intValue();
            log.info("Evento pago.registrado recibido para orden {}", idOrden);

            ordenUseCase.cambiarEstadoPorEvento(idOrden, EstadoOrden.PAGADO.getDescripcion())
                    .doOnSuccess(v -> log.info("Orden {} marcada como Pagado", idOrden))
                    .doOnError(e -> log.error("Error al cambiar estado de orden {}: {}", idOrden, e.getMessage()))
                    .subscribe();
        } catch (Exception e) {
            log.error("Error procesando evento pago.registrado: {}", e.getMessage(), e);
        }
    }
}