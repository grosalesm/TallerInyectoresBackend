package com.taller.auth.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idRol;
    private String nombre = "";
}