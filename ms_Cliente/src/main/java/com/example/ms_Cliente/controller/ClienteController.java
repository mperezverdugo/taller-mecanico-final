package com.example.ms_Cliente.controller;

import com.example.ms_Cliente.exception.RecursoNoEncontradoException;
import com.example.ms_Cliente.model.Cliente;
import com.example.ms_Cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor

public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        return clientes.isEmpty() ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(201).body(clienteService.guardar(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // 1. Buscamos al cliente. Si no existe, lanzamos nuestro "grito" personalizado.
        clienteService.obtenerPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede eliminar: El cliente con ID " + id + " no existe."));

        // 2. Si el código llega aquí, significa que el cliente sí existe. Procedemos a borrarlo.
        clienteService.eliminar(id);

        // 3. Respondemos con un 204 (No Content), que significa "Listo, lo borré y no tengo nada más que mostrarte".
        return ResponseEntity.noContent().build();
    }
}

