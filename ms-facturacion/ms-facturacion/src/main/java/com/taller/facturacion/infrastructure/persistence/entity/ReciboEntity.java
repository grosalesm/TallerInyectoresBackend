package com.taller.facturacion.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recibos")
public class ReciboEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recibo")
    private Integer idRecibo;

    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "num_operacion")
    private String numOperacion;

    @Column(name = "numero_recibo")
    private String numeroRecibo;
}