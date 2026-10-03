package com.taller.facturacion.infrastructure.persistence.adapter;

import com.taller.facturacion.application.port.outservice.ReciboEventOutService;
import com.taller.facturacion.domain.bean.Recibo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReciboEventAdapter implements ReciboEventOutService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicarPagoRegistrado(Recibo recibo) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idRecibo", recibo.getIdRecibo());
        evento.put("idOrden", recibo.getIdOrden());
        evento.put("monto", recibo.getMonto());
        evento.put("metodoPago", recibo.getMetodoPago());
        evento.put("numOperacion", recibo.getNumOperacion());
        evento.put("numeroRecibo", recibo.getNumeroRecibo());
        evento.put("fechaPago", recibo.getFechaPago() != null
                ? recibo.getFechaPago().toString()
                : LocalDateTime.now().toString());
        evento.put("fechaEvento", LocalDateTime.now().toString());

        rabbitTemplate.convertAndSend("facturacion.exchange", "pago.registrado", evento);
    }
}