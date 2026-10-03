package com.taller.catalogos.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inyector implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idInyector;
    private String modelo = "";
    private String marca = "";
    private String descripcion = "";
    private Boolean activo = true;
}