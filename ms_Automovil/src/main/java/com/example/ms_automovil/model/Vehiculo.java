package com.example.ms_automovil.model;

import jakarta.persistence.*;
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
    @Column(nullable = false, unique = true, length = 10)
    private String patente;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    // Año de fabricación
    @Column(nullable = false)
    private Integer anio;

    // --- EL CAMPO DE CONEXIÓN ---
    // ID del cliente que está en ms_Cliente

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
}
