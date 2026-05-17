package com.example.ms_ordentrabajo.dto;

import lombok.Data;

@Data
public class MecanicoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String especialidad;
    private String estado;
}