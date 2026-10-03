package com.taller.catalogos.infrastructure.persistence.adapter;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.bean.Servicio;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CatalogoEventAdapter implements CatalogoEventOutService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publicarInyectorActualizado(Inyector i) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idInyector", i.getIdInyector());
        evento.put("modelo", i.getModelo());
        evento.put("marca", i.getMarca());
        evento.put("descripcion", i.getDescripcion());
        evento.put("activo", i.getActivo());
        evento.put("fechaEvento", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("catalogo.exchange", "catalogo.inyector.actualizado", evento);
    }

    @Override
    public void publicarServicioActualizado(Servicio s) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idServicio", s.getIdServicio());
        evento.put("nombre", s.getNombre());
        evento.put("descripcion", s.getDescripcion());
        evento.put("precioBase", s.getPrecioBase());
        evento.put("activo", s.getActivo());
        evento.put("fechaEvento", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("catalogo.exchange", "catalogo.servicio.actualizado", evento);
    }

    @Override
    public void publicarMecanicoActualizado(Mecanico m) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("idMecanico", m.getIdMecanico());
        evento.put("nombres", m.getNombres());
        evento.put("apellidos", m.getApellidos());
        evento.put("especialidad", m.getEspecialidad());
        evento.put("telefono", m.getTelefono());
        evento.put("activo", m.getActivo());
        evento.put("fechaEvento", LocalDateTime.now().toString());
        rabbitTemplate.convertAndSend("catalogo.exchange", "catalogo.mecanico.actualizado", evento);
    }
}