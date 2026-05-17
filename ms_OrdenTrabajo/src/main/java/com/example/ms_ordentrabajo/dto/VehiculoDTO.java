package com.example.ms_ordentrabajo.dto;

import lombok.Data;

@Data
public class VehiculoDTO {
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private Long clienteId;
}
