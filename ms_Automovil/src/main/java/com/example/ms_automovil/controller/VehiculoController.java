package com.example.ms_automovil.controller;

import com.example.ms_automovil.model.Vehiculo;
import com.example.ms_automovil.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Módulo Vehículos", description = "Operaciones CRUD para la gestión y control del parque automotriz del taller")
public class VehiculoController {


    //Creacion del objeto que viene del Service

    @Autowired
    private VehiculoService vehiculoService;

    //Documentar el listar todos los Vehiculos
    @Operation(summary = "Listar todos los vehículos", description = "Retorna el listado completo con todos los vehículos registrados en el sistema del taller mecánico.")
    @ApiResponse(responseCode = "200", description = "Listado de vehículos recuperado con éxito")

    //Listar todos los Vehiculos

    @GetMapping
    public List<Vehiculo> listar() {
        return vehiculoService.listarTodos();
    }



    //Documentar el Guardar
    @Operation(summary = "Guardar un nuevo vehículo", description = "Registra un vehículo en el taller vinculándolo al ID de un cliente existente y validando que la patente no esté duplicada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo guardado exitosamente en el sistema"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error en las validaciones lógicas de entrada, patente duplicada o cliente inexistente")
    })

    //Guardar
    @PostMapping
    public ResponseEntity<Vehiculo> guardar(@Valid @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.ok(vehiculoService.guardar(vehiculo));
    }



    //Documentar el Buscar por id
    @Operation(summary = "Buscar vehículo por ID", description = "Busca y recupera los datos de un vehículo específico basándose en su identificador primario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado y retornado con éxito"),
            @ApiResponse(responseCode = "404", description = "Not Found: No existe ningún vehículo con el ID proporcionado")
    })

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.buscarPorId(id));
    }



    //Documentar el Buscar por patente
    @Operation(summary = "Buscar vehículo por patente", description = "Busca y recupera un registro único de vehículo basándose en su patente alfanumérica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado y retornado con éxito"),
            @ApiResponse(responseCode = "404", description = "Not Found: La patente proporcionada no se encuentra en el sistema")
    })


    //Buscar por patente
    @GetMapping("/patente/{patente}")
    public ResponseEntity<Vehiculo> buscarPorPatente(@PathVariable String patente) {
        return ResponseEntity.ok(vehiculoService.buscarPorPatente(patente));
    }





    //Documentar el Actualizar
    @Operation(summary = "Actualizar datos de un vehículo", description = "Modifica y actualiza la información técnica de un vehículo existente localizándolo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Estructura del cuerpo de la petición inválida"),
            @ApiResponse(responseCode = "404", description = "Not Found: El vehículo con el ID especificado no fue encontrado")
    })

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @Valid @RequestBody Vehiculo detalles) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, detalles));
    }




    //Documentar el Eliminar
    @Operation(summary = "Eliminar un vehículo", description = "Remueve permanentemente el registro de un automóvil del taller mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehículo eliminado con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "Not Found: Intento de eliminar un vehículo que no existe")
    })

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}