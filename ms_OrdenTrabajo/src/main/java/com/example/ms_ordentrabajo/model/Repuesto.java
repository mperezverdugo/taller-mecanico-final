package com.example.ms_ordentrabajo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "repuestos")
public class Repuesto {


    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Nombre
    @NotBlank(message = "El nombre del repuesto es obligatorio")
    private String nombre;


    //Cantidad
    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;


    //Precio unitario
    @NotNull(message = "El precio unitario es obligatorio")
    @Column(name = "precio_unitario")
    private Double precioUnitario;


    //Orden ID
    @Column(name = "orden_id")
    private Long ordenId;
}