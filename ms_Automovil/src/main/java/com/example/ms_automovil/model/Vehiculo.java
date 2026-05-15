package com.example.ms_automovil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patente única

    @NotBlank(message = "La patente es obligatoria")
    @Size(max = 10, message = "La patente no puede superar los 10 caracteres")
    @Column(nullable = false, unique = true, length = 10)
    private String patente;


    //Marca
    @NotBlank(message = "La marca es obligatoria")
    @Column(nullable = false, length = 50)
    private String marca;


    //Modelo

    @NotBlank(message = "El modelo es obligatorio")
    @Column(nullable = false, length = 50)
    private String modelo;



    // Año de fabricación

    @NotNull(message = "El año de fabricación es obligatorio")
    @Column(nullable = false)
    private Integer anio;



    // --- EL CAMPO DE CONEXIÓN ---
    // ID del cliente que está en ms_Cliente


    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
}
