package com.example.ms_automovil.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String telefono;
    private String correo;
}
