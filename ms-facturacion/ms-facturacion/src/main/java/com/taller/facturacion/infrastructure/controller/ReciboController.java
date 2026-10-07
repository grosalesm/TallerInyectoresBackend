package com.taller.facturacion.infrastructure.controller;

import com.taller.facturacion.application.port.outservice.ClienteOutService;
import com.taller.facturacion.application.port.outservice.DetalleOutService;
import com.taller.facturacion.application.port.outservice.MecanicoOutService;
import com.taller.facturacion.application.port.outservice.OrdenOutService;
import com.taller.facturacion.application.port.usecase.ReciboUseCase;
import com.taller.facturacion.domain.bean.OrdenInfo;
import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.infrastructure.dto.PeticionPago;
import com.taller.facturacion.infrastructure.dto.ReciboResponse;
import com.taller.facturacion.infrastructure.dto.RespuestaPago;
import com.taller.facturacion.infrastructure.mapper.ReciboWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recibo")
@RequiredArgsConstructor
public class ReciboController {

    private final ReciboUseCase reciboUseCase;
    private final OrdenOutService ordenOutService;
    private final ClienteOutService clienteOutService;
    private final MecanicoOutService mecanicoOutService;
    private final DetalleOutService detalleOutService;
    private final ReciboWebMapper reciboWebMapper;

    @GetMapping
    public List<ReciboResponse> listar() {
        return reciboUseCase.listar().stream()
                .map(this::enriquecerConDatosExternos)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReciboResponse> obtenerPorId(@PathVariable Integer id) {
        return reciboUseCase.obtenerPorId(id)
                .map(this::enriquecerConDatosExternos)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<ReciboResponse> obtenerPorOrden(@PathVariable Integer idOrden) {
        return reciboUseCase.obtenerPorOrden(idOrden)
                .map(this::enriquecerConDatosExternos)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/mes/{mes}/{anio}")
    public List<ReciboResponse> listarPorMes(@PathVariable int mes, @PathVariable int anio) {
        return reciboUseCase.listarPorMes(mes, anio).stream()
                .map(this::enriquecerConDatosExternos)
                .toList();
    }

    @PostMapping
    public ResponseEntity<RespuestaPago> registrar(@Valid @RequestBody PeticionPago request) {
        Recibo recibo = reciboUseCase.registrar(
                request.getIdOrden(), request.getMetodoPago(), request.getNumOperacion());
        ReciboResponse response = enriquecerConDatosExternos(recibo);

        RespuestaPago respuesta = new RespuestaPago();
        respuesta.setMensaje("Pago registrado correctamente.");
        respuesta.setRecibo(response);
        return ResponseEntity.ok(respuesta);
    }

    private ReciboResponse enriquecerConDatosExternos(Recibo recibo) {
        ReciboResponse response = reciboWebMapper.toResponse(recibo);

        OrdenInfo orden = ordenOutService.obtenerOrden(recibo.getIdOrden())
                .orElse(new OrdenInfo());

        String nombreCliente = "";
        String dniCliente = "";
        if (orden.getIdCliente() != null) {
            nombreCliente = clienteOutService.obtenerNombreCliente(orden.getIdCliente());
            dniCliente = clienteOutService.obtenerDniCliente(orden.getIdCliente());
        }

        String nombreMecanico = "";
        if (orden.getIdMecanico() != null) {
            nombreMecanico = mecanicoOutService.obtenerNombreMecanico(orden.getIdMecanico());
        }

        List<Map<String, Object>> inyectores =
                detalleOutService.obtenerInyectoresPorOrden(recibo.getIdOrden());
        List<Map<String, Object>> servicios =
                detalleOutService.obtenerServiciosPorOrden(recibo.getIdOrden());

        response.setNombreCliente(nombreCliente != null ? nombreCliente : "");
        response.setDniCliente(dniCliente != null ? dniCliente : "");
        response.setNombreMecanico(nombreMecanico != null ? nombreMecanico : "");
        response.setInyectores(inyectores != null ? inyectores : List.of());
        response.setServicios(servicios != null ? servicios : List.of());
        return response;
    }
}