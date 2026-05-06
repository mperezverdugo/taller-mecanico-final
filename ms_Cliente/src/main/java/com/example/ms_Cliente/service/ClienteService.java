package com.example.ms_Cliente.service;

import com.example.ms_Cliente.model.Cliente;
import com.example.ms_Cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        log.info("Obteniendo lista de todos los clientes");
        return clienteRepository.findAll();
    }


    //Buscar por id
    // Cambiamos a Cliente directamente. Si no existe, lanza el error aquí.
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error: Cliente con ID {} no encontrado", id); // Log de trazabilidad
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado en el sistema.");
                });
    }


    //Guardar
    @Transactional
    public Cliente guardar(Cliente cliente) {
        log.info("Guardando nuevo cliente con RUT: {}", cliente.getRut());
        return clienteRepository.save(cliente);
    }


    //Eliminar
    @Transactional
    public void eliminar(Long id) {
        // Primero usamos el método de arriba para validar si existe
        Cliente cliente = buscarPorId(id);
        log.info("Eliminando cliente con ID: {}", id);
        clienteRepository.delete(cliente);
    }


    //Actualizar
    @Transactional
    public Cliente actualizar(Long id, Cliente clienteActualizado) {

        // Llama a buscarPorId(), que lanza ResourceNotFoundException si no existe.
        Cliente clienteExistente = buscarPorId(id);

        log.info("Actualizando datos del cliente con ID: {}", id);

        clienteExistente.setRut(clienteActualizado.getRut());
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        clienteExistente.setCorreo(clienteActualizado.getCorreo());

        return clienteRepository.save(clienteExistente);
    }

}