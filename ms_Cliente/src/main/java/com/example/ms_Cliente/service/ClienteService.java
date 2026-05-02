package com.example.ms_Cliente.service;

import com.example.ms_Cliente.model.Cliente;
import com.example.ms_Cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // Para logs estructurados
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        log.info("Obteniendo lista de todos los clientes");
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional // Asegura integridad
    public Cliente guardar(Cliente cliente) {
        log.info("Guardando nuevo cliente con RUT: {}", cliente.getRut());
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
