package com.taller.ordenes.infrastructure.persistence.adapter;

import com.taller.ordenes.application.port.outservice.OrdenEventOutService;
import com.taller.ordenes.domain.bean.Orden;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdenEventAdapter implements OrdenEventOutService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicarOrdenCreada(Orden orden) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idOrden", orden.getIdOrden());
        evento.put("idCliente", orden.getIdCliente());
        evento.put("idMecanico", orden.getIdMecanico());
        evento.put("fechaIngreso", orden.getFechaIngreso() != null
                ? orden.getFechaIngreso().toString()
                : LocalDateTime.now().toString());
        evento.put("estado", orden.getEstado());
        evento.put("fechaEvento", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("orden.exchange", "orden.creada", evento);
    }

    @Override
    public void publicarEstadoCambiado(Integer idOrden, String estadoAnterior, String estadoNuevo) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idOrden", idOrden);
        evento.put("estadoAnterior", estadoAnterior);
        evento.put("estadoNuevo", estadoNuevo);
        evento.put("fechaCambio", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("orden.exchange", "orden.estado.cambiada", evento);
    }
}