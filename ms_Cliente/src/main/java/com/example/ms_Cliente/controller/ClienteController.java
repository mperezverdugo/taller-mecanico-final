package com.example.ms_Cliente.controller;

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
        // Código 201 Created para creaciones exitosas
        return ResponseEntity.status(201).body(clienteService.guardar(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        // El Service se encarga de lanzar el 404 si no existe
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        // Delega la lógica al Service y retorna el cliente actualizado con HTTP 200.
        Cliente clienteActualizado = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
}