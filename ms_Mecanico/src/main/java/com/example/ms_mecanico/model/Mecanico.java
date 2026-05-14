package com.example.ms_mecanico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "mecanicos")
@Data

public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, length = 12)
    private String rut;

    @NotBlank
    @Column(length = 50)
    private String nombre;

    @NotBlank
    @Column(length = 50)
    private String especialidad;

    @NotBlank
    @Column(length = 20)
    private String estado;
}