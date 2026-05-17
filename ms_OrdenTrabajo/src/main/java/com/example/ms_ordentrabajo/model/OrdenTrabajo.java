package com.example.ms_ordentrabajo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate; // O java.time.LocalDate

@Data
@Entity
@Table(name = "orden_trabajo")
public class OrdenTrabajo {


    //ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Fecha de Ingreso
    @NotNull(message = "La fecha de ingreso es obligatoria")
    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;


    //Descripcion de la falla
    @NotBlank(message = "La descripción de la falla es obligatoria")
    @Column(name = "descripcion_falla")
    private String descripcionFalla;


    //Estado de la orden
    @NotBlank(message = "El estado de la orden es obligatorio")
    @Size(max = 20)
    private String estado;


    //Costo estimado
    @NotNull(message = "El costo estimado no puede ser nulo")
    @Column(name = "costo_estimado")
    private Double costoEstimado;


    //Id del mecanico
    @NotNull(message = "El ID del mecánico es obligatorio")
    @Column(name = "mecanico_id")
    private Long mecanicoId;



    //Patente del vehiculo

    @NotBlank(message = "La patente del vehículo es obligatoria")
    @Size(max = 10)
    @Column(name = "patente_vehiculo")
    private String patenteVehiculo;
}