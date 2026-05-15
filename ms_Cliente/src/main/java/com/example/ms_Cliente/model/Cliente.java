package com.example.ms_Cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "clientes")


    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;




        //RUT

        @NotBlank(message = "El RUT es obligatorio")
        @Size(max = 12, message = "El RUT no puede superar los 12 caracteres")
        @Column(nullable = false, unique = true, length = 12)
        private String rut;


        //Nombre

        @NotBlank(message = "El nombre es obligatorio")
        @Column(nullable = false, length = 100)
        private String nombre;


        //Telefono

        @NotBlank
        @Column(length = 20)
        private String telefono;


        //Correo

        @Size(max = 100, message = "El correo es demasiado largo")
        @Column(length = 100)
        private String correo;
    }

