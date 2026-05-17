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
        // Revision de la patente
        if (vehiculoRepository.findByPatente(vehiculo.getPatente().toUpperCase()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La patente ya está registrada.");
        }

        // Revision de la existencia del cliente en ms_cliente
        try {
            // Pregunta a ms_Cliente por este ID
            clienteFeignClient.obtenerClientePorId(vehiculo.getClienteId());
        } catch (Exception e) {
            // Si ms_Cliente dice "ese ID no existe", se detiene el guardado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede registrar el vehículo: El cliente con ID " + vehiculo.getClienteId() + " no existe.");
        }

        // GUARDAR
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
        Vehiculo vehiculo = buscarPorId(id); // Valida que el auto exista

        // Antes de cambiar el dueño, se verifica que el nuevo cliente exista
        try {
            clienteFeignClient.obtenerClientePorId(detalles.getClienteId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se puede actualizar: El cliente con ID " + detalles.getClienteId() + " no existe.");
        }

        // Si el cliente existe, actualizamos los datos
        vehiculo.setMarca(detalles.getMarca());
        vehiculo.setModelo(detalles.getModelo());
        vehiculo.setAnio(detalles.getAnio());
        vehiculo.setClienteId(detalles.getClienteId()); // Se asigna el nuevo ID validado

        return vehiculoRepository.save(vehiculo);
    }


    //Eliminar

    public void eliminar(Long id) {
        Vehiculo vehiculo = buscarPorId(id); // Validamos que exista antes de intentar borrar
        vehiculoRepository.delete(vehiculo);
    }
}