package com.example.ms_automovil.service;

import com.example.ms_automovil.client.ClienteFeignClient;
import com.example.ms_automovil.model.Vehiculo;
import com.example.ms_automovil.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VehiculoService {



    //Conexion con OpenFeign

    @Autowired
    private ClienteFeignClient clienteFeignClient;



    //Creacion del objeto que viene del Repository

    @Autowired
    private VehiculoRepository vehiculoRepository;


    // Obtener todos los Vehiculos

    public List<Vehiculo> listarTodos() {

        return vehiculoRepository.findAll();
    }



    //Guardar

    public Vehiculo guardar(Vehiculo vehiculo) {
        if (vehiculoRepository.findByPatente(vehiculo.getPatente().toUpperCase()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La patente ya está registrada.");
        }

        // validación estricta del cliente
        try {
            var cliente = clienteFeignClient.obtenerClientePorId(vehiculo.getClienteId());

            // Si el servicio de clientes responde 200 pero el objeto viene vacío (null)
            if (cliente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se puede registrar el vehículo: El cliente con ID " + vehiculo.getClienteId() + " no existe.");
            }
        } catch (ResponseStatusException ex) {
            throw ex; // Re-lanza la excepción de arriba
        } catch (Exception e) {
            // Ataja fallos de red, errores 404/500 de Feign
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede registrar el vehículo: El cliente con ID " + vehiculo.getClienteId() + " no responde o no existe.");
        }

        vehiculo.setPatente(vehiculo.getPatente().toUpperCase());
        return vehiculoRepository.save(vehiculo);
    }





    //Buscar por ID

    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehículo con ID " + id + " no encontrado."));
    }



    //Buscar por Patente

    public Vehiculo buscarPorPatente(String patente) {
        Vehiculo v = vehiculoRepository.findByPatente(patente.toUpperCase());
        if (v == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La patente " + patente + " no existe.");
        }
        return v;
    }



    //Actualizar

    public Vehiculo actualizar(Long id, Vehiculo detalles) {
        Vehiculo vehiculo = buscarPorId(id);

        try {
            var cliente = clienteFeignClient.obtenerClientePorId(detalles.getClienteId());
            if (cliente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se puede actualizar: El cliente con ID " + detalles.getClienteId() + " no existe.");
            }
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede actualizar: El cliente con ID " + detalles.getClienteId() + " no existe o el servicio no responde.");
        }

        vehiculo.setMarca(detalles.getMarca());
        vehiculo.setModelo(detalles.getModelo());
        vehiculo.setAnio(detalles.getAnio());
        vehiculo.setClienteId(detalles.getClienteId());

        return vehiculoRepository.save(vehiculo);
    }


    //Eliminar

    public void eliminar(Long id) {
        Vehiculo vehiculo = buscarPorId(id); // Validamos que exista antes de intentar borrar
        vehiculoRepository.delete(vehiculo);
    }
}