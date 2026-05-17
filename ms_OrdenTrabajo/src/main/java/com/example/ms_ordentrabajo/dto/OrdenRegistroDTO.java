package com.example.ms_ordentrabajo.dto;

import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.model.Repuesto;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.List;


@Data
public class OrdenRegistroDTO {

    @Valid
    private OrdenTrabajo orden;

    @Valid
    private List<Repuesto> repuestos;
}