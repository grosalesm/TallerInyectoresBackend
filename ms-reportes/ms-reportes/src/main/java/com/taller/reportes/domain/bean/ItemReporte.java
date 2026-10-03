package com.taller.reportes.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemReporte implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre = "";
    private Integer cantidad = 0;
    private Double total = 0.0;
}