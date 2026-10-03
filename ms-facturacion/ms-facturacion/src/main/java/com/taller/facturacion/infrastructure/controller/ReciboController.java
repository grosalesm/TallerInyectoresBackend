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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<ReciboResponse> listar() {
        return reciboUseCase.listar().flatMap(this::enriquecerConDatosExternos);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ReciboResponse>> obtenerPorId(@PathVariable Integer id) {
        return reciboUseCase.obtenerPorId(id)
                .flatMap(this::enriquecerConDatosExternos)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/orden/{idOrden}")
    public Mono<ResponseEntity<ReciboResponse>> obtenerPorOrden(@PathVariable Integer idOrden) {
        return reciboUseCase.obtenerPorOrden(idOrden)
                .flatMap(this::enriquecerConDatosExternos)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/mes/{mes}/{anio}")
    public Flux<ReciboResponse> listarPorMes(@PathVariable int mes, @PathVariable int anio) {
        return reciboUseCase.listarPorMes(mes, anio).flatMap(this::enriquecerConDatosExternos);
    }

    @PostMapping
    public Mono<ResponseEntity<RespuestaPago>> registrar(@Valid @RequestBody PeticionPago request) {
        return reciboUseCase.registrar(request.getIdOrden(), request.getMetodoPago(), request.getNumOperacion())
                .flatMap(this::enriquecerConDatosExternos)
                .map(response -> {
                    RespuestaPago respuesta = new RespuestaPago();
                    respuesta.setMensaje("Pago registrado correctamente.");
                    respuesta.setRecibo(response);
                    return ResponseEntity.ok(respuesta);
                });
    }

    private Mono<ReciboResponse> enriquecerConDatosExternos(Recibo recibo) {
        ReciboResponse response = reciboWebMapper.toResponse(recibo);

        Mono<OrdenInfo> ordenMono = ordenOutService.obtenerOrden(recibo.getIdOrden())
                .defaultIfEmpty(new OrdenInfo());

        return ordenMono.flatMap(orden -> {
            Mono<String> nombreClienteMono = orden.getIdCliente() != null
                    ? clienteOutService.obtenerNombreCliente(orden.getIdCliente()).defaultIfEmpty("")
                    : Mono.just("");
            Mono<String> dniClienteMono = orden.getIdCliente() != null
                    ? clienteOutService.obtenerDniCliente(orden.getIdCliente()).defaultIfEmpty("")
                    : Mono.just("");
            Mono<String> nombreMecanicoMono = orden.getIdMecanico() != null
                    ? mecanicoOutService.obtenerNombreMecanico(orden.getIdMecanico()).defaultIfEmpty("")
                    : Mono.just("");
            Mono<java.util.List<java.util.Map<String, Object>>> inyectoresMono =
                    detalleOutService.obtenerInyectoresPorOrden(recibo.getIdOrden()).defaultIfEmpty(java.util.List.of());
            Mono<java.util.List<java.util.Map<String, Object>>> serviciosMono =
                    detalleOutService.obtenerServiciosPorOrden(recibo.getIdOrden()).defaultIfEmpty(java.util.List.of());

            return Mono.zip(nombreClienteMono, dniClienteMono, nombreMecanicoMono, inyectoresMono, serviciosMono)
                    .map(tuple -> {
                        response.setNombreCliente(tuple.getT1());
                        response.setDniCliente(tuple.getT2());
                        response.setNombreMecanico(tuple.getT3());
                        response.setInyectores(tuple.getT4());
                        response.setServicios(tuple.getT5());
                        return response;
                    });
        });
    }
}