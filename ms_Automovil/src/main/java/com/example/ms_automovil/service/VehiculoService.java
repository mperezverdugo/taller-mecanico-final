package com.example.ms_automovil.service;

import com.example.ms_automovil.model.Vehiculo;
import com.example.ms_automovil.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        // Validar si la patente ya existe antes de guardar (Regla de negocio extra)
        if (vehiculoRepository.findByPatente(vehiculo.getPatente().toUpperCase()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La patente ya está registrada en el sistema.");
        }
        vehiculo.setPatente(vehiculo.getPatente().toUpperCase());
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo con ID " + id + " no encontrado."));
    }

    public Vehiculo buscarPorPatente(String patente) {
        Vehiculo v = vehiculoRepository.findByPatente(patente.toUpperCase());
        if (v == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La patente " + patente + " no existe.");
        }
        return v;
    }

    public Vehiculo actualizar(Long id, Vehiculo detalles) {
        Vehiculo vehiculo = buscarPorId(id); // Si no existe, buscarPorId ya lanza el 404 por nosotros

        vehiculo.setMarca(detalles.getMarca());
        vehiculo.setModelo(detalles.getModelo());
        vehiculo.setAnio(detalles.getAnio());
        vehiculo.setClienteId(detalles.getClienteId());
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Long id) {
        Vehiculo vehiculo = buscarPorId(id); // Validamos que exista antes de intentar borrar
        vehiculoRepository.delete(vehiculo);
    }
}