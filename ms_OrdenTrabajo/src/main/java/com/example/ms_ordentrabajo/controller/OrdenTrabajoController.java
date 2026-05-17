package com.example.ms_ordentrabajo.controller;

import com.example.ms_ordentrabajo.dto.OrdenRegistroDTO;
import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.service.OrdenTrabajoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenTrabajoController {

    @Autowired
    private OrdenTrabajoService ordenTrabajoService;

    // Endpoint para crear una orden con sus repuestos
    @PostMapping
    public ResponseEntity<OrdenTrabajo> crearOrden(@Valid @RequestBody OrdenRegistroDTO registro) {
        // Llama al método del servicio que calcula todo y valida con Feign
        OrdenTrabajo nuevaOrden = ordenTrabajoService.registrarOrdenCompleta(registro);
        // Retorna un estado 201 Created junto con la orden guardada
        return ResponseEntity.status(201).body(nuevaOrden);
    }

    // Endpoint para listar todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> listarTodas() {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerTodas();
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si no hay datos
        }
        return ResponseEntity.ok(ordenes); // 200 con la lista
    }
}