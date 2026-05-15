package com.example.ms_mecanico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "mecanicos")
@Data

public class Mecanico {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //RUT
    @NotBlank
    @Column(unique = true, length = 12)
    private String rut;


    //Nombre
    @NotBlank
    @Column(length = 50)
    private String nombre;


    //Especialidad
    @NotBlank
    @Column(length = 50)
    private String especialidad;


    //Estado
    @NotBlank
    @Column(length = 20)
    private String estado;
}