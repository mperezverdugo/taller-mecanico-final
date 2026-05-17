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

    // Endpoint para buscar una orden única por ID (GET /api/ordenes/1)
    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenTrabajoService.obtenerPorId(id));
    }

    // Endpoint para buscar órdenes de una patente (GET /api/ordenes/vehiculo/BJKL-45)
    @GetMapping("/vehiculo/{patente}")
    public ResponseEntity<List<OrdenTrabajo>> buscarPorPatente(@PathVariable String patente) {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerPorPatente(patente);
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si ese auto no tiene historial
        }
        return ResponseEntity.ok(ordenes); // 200 con la lista de órdenes del auto
    }

    // Endpoint para actualizar el estado (PATCH /api/ordenes/1/estado?nuevo=EN_PROCESO)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenTrabajo> cambiarEstado(@PathVariable Long id, @RequestParam String nuevo) {
        OrdenTrabajo ordenActualizada = ordenTrabajoService.actualizarEstadoOrden(id, nuevo);
        return ResponseEntity.ok(ordenActualizada);
    }




















}


