package com.taller.clientes.infrastructure.persistence.adapter;

import com.taller.clientes.application.port.outservice.ClienteEventOutService;
import com.taller.clientes.domain.bean.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClienteEventAdapter implements ClienteEventOutService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicarClienteCreado(Cliente cliente) {
        rabbitTemplate.convertAndSend("cliente.exchange", "cliente.creado", payload(cliente));
    }

    @Override
    public void publicarClienteActualizado(Cliente cliente) {
        rabbitTemplate.convertAndSend("cliente.exchange", "cliente.actualizado", payload(cliente));
    }

    @Override
    public void publicarClienteEliminado(Integer idCliente) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idCliente", idCliente);
        evento.put("fechaEvento", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("cliente.exchange", "cliente.eliminado", evento);
    }

    private Map<String, Object> payload(Cliente c) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idCliente", c.getIdCliente());
        evento.put("nombres", c.getNombres());
        evento.put("apellidos", c.getApellidos());
        evento.put("dni", c.getDni());
        evento.put("telefono", c.getTelefono());
        evento.put("email", c.getEmail());
        evento.put("fechaEvento", LocalDateTime.now().toString());
        return evento;
    }
}