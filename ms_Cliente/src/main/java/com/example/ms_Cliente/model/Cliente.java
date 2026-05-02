package com.example.ms_Cliente.model;

import jakarta.persistence.*;
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

        // Validación de base de datos
        @Column(nullable = false, unique = true, length = 12)
        private String rut;

        @Column(nullable = false, length = 100)
        private String nombre;

        @Column(length = 20)
        private String telefono;

        @Column(length = 100)
        private String correo;
    }

