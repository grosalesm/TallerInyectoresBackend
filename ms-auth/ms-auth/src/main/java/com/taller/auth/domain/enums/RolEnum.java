package com.taller.auth.domain.enums;

public enum RolEnum {
    ADMINISTRADOR(1, "Administrador");

    private final Integer id;
    private final String nombre;

    RolEnum(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}