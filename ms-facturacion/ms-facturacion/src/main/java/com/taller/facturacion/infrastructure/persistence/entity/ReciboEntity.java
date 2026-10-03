package com.taller.facturacion.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("recibos")
public class ReciboEntity {

    @Id
    @Column("id_recibo")
    private Integer idRecibo;

    @Column("id_orden")
    private Integer idOrden;

    @Column("fecha_pago")
    private LocalDateTime fechaPago;

    @Column("monto")
    private Double monto;

    @Column("metodo_pago")
    private String metodoPago;

    @Column("num_operacion")
    private String numOperacion;

    @Column("numero_recibo")
    private String numeroRecibo;
}