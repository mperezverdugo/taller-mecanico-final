package com.example.ms_automovil.controller;

import com.example.ms_automovil.model.Vehiculo;
import com.example.ms_automovil.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {


    //Creacion del objeto que viene del Service

    @Autowired
    private VehiculoService vehiculoService;


    //Listar todos los Vehiculos

    @GetMapping
    public List<Vehiculo> listar() {
        return vehiculoService.listarTodos();
    }


    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@Valid @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.guardar(vehiculo));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.buscarPorPatente(patente));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @Valid @RequestBody Vehiculo detalles) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, detalles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}