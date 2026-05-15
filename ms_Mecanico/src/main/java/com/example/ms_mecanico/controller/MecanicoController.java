package com.example.ms_mecanico.controller;

import com.example.ms_mecanico.model.Mecanico;
import com.example.ms_mecanico.service.MecanicoService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mecanicos")
@RequiredArgsConstructor

public class MecanicoController {

    private final MecanicoService mecanicoService;


    //Listar
    @GetMapping
    public ResponseEntity<List<Mecanico>> listar() {

        List<Mecanico> mecanicos = mecanicoService.obtenerTodos();

        if (mecanicos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 - No hay mecanicos
        }
        return ResponseEntity.ok(mecanicos); // 200 - Lista de mecanicos
    }


    //Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(mecanicoService.buscarPorId(id));

    }


    //Crear
    @PostMapping
    public ResponseEntity<Mecanico> crear(@Valid @RequestBody Mecanico mecanico) {
        // Código 201 Created para creaciones exitosas
        return ResponseEntity.status(201).body(mecanicoService.guardar(mecanico));
    }


    //Actualizar

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> actualizar(@PathVariable Long id, @Valid @RequestBody Mecanico mecanico) {
        // El Service tiene la logica  y retorna el cliente actualizado con HTTP 200.
        Mecanico mecanicoActualizado = mecanicoService.actualizar(id, mecanico);
        return ResponseEntity.ok(mecanicoActualizado);
    }


    //Eliminar

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mecanicoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
















}
