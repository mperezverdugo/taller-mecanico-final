package com.example.ms_mecanico.controller;

import com.example.ms_mecanico.model.Mecanico;
import com.example.ms_mecanico.service.MecanicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mecanicos")
@RequiredArgsConstructor
@Tag(name = "Módulo Mecánicos", description = "Operaciones CRUD para la administración del personal técnico del taller")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    //Documentar el Listar
    @Operation(summary = "Listar todos los mecánicos", description = "Retorna el personal técnico registrado en el taller. Si no existen registros en el sistema, devuelve un código HTTP 204.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de mecánicos recuperado con éxito"),
            @ApiResponse(responseCode = "204", description = "No Content: No hay mecánicos registrados en la base de datos")
    })
    //Listar
    @GetMapping
    public ResponseEntity<List<Mecanico>> listar() {

        List<Mecanico> mecanicos = mecanicoService.obtenerTodos();

        if (mecanicos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 - No hay mecanicos
        }
        return ResponseEntity.ok(mecanicos); // 200 - Lista de mecanicos
    }



    //Documentar el Buscar
    @Operation(summary = "Buscar mecánico por ID", description = "Busca y recupera el perfil de un mecánico específico basado en su identificador primario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mecánico encontrado y retornado con éxito"),
            @ApiResponse(responseCode = "404", description = "Not Found: No existe ningún mecánico con el ID proporcionado")
    })


    //Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(mecanicoService.buscarPorId(id));

    }




    //Documentar el Crear
    @Operation(summary = "Crear un nuevo mecánico", description = "Registra un técnico en el taller validando los campos obligatorios mediante validación estricta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mecánico creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error en las validaciones de entrada o datos corruptos")
    })



    //Crear
    @PostMapping
    public ResponseEntity<Mecanico> crear(@Valid @RequestBody Mecanico mecanico) {
        // Código 201 Created para creaciones exitosas
        return ResponseEntity.status(201).body(mecanicoService.guardar(mecanico));
    }




    //Documentar el Actualizar
    @Operation(summary = "Actualizar datos de un mecánico", description = "Modifica y actualiza la información de un técnico existente localizándolo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mecánico actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Estructura del cuerpo de la petición inválida"),
            @ApiResponse(responseCode = "404", description = "Not Found: El mecánico con el ID especificado no fue encontrado")
    })


    //Actualizar

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> actualizar(@PathVariable Long id, @Valid @RequestBody Mecanico mecanico) {
        // El Service tiene la logica  y retorna el cliente actualizado con HTTP 200.
        Mecanico mecanicoActualizado = mecanicoService.actualizar(id, mecanico);
        return ResponseEntity.ok(mecanicoActualizado);
    }







    //Documentar el Eliminar
    @Operation(summary = "Eliminar un mecánico", description = "Remueve permanentemente el registro de un técnico de la base de datos mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mecánico eliminado con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "Not Found: Intento de eliminar un mecánico que no existe")
    })

    //Eliminar

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mecanicoService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



















}
