package com.example.ms_ordentrabajo.controller;

import com.example.ms_ordentrabajo.dto.OrdenRegistroDTO;
import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.service.OrdenTrabajoService;
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
@RequestMapping("/api/ordenes")
@Tag(name = "Módulo Órdenes de Trabajo", description = "Operaciones del núcleo de negocio del taller: Emisión, costeo de repuestos e historial técnico")
public class OrdenTrabajoController {

    @Autowired
    private OrdenTrabajoService ordenTrabajoService;

    // Documentar el Endpoint para crear una orden con sus repuestos
    @Operation(summary = "Crear una orden con sus repuestos", description = "Permite registrar una orden completa calculando el costo total y validando mediante OpenFeign que la patente del auto y el ID del mecánico existan en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden de trabajo creada y registrada con éxito"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error en las validaciones de datos de entrada"),
            @ApiResponse(responseCode = "404", description = "Not Found: La patente del vehículo o el ID del mecánico no existen de forma remota")
    })

    // Endpoint para crear una orden con sus repuestos
    @PostMapping
    public ResponseEntity<OrdenTrabajo> crearOrden(@Valid @RequestBody OrdenRegistroDTO registro) {
        // Llama al método del servicio que calcula todo y valida con Feign
        OrdenTrabajo nuevaOrden = ordenTrabajoService.registrarOrdenCompleta(registro);
        // Retorna un estado 201 Created junto con la orden guardada
        return ResponseEntity.status(201).body(nuevaOrden);
    }


    //Documentar el endpoint para listar todas las órdenes
    @Operation(summary = "Listar todas las órdenes", description = "Retorna el historial completo de todas las órdenes de trabajo emitidas en el taller. Devuelve 204 si la base de datos no registra órdenes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado global de órdenes recuperado con éxito"),
            @ApiResponse(responseCode = "204", description = "No Content: No existen órdenes registradas en el sistema")
    })

    // Endpoint para listar todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrdenTrabajo>> listarTodas() {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerTodas();
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si no hay datos
        }
        return ResponseEntity.ok(ordenes); // 200 con la lista
    }



    //Documentar el endopoint para buscar una orden por su ID
    @Operation(summary = "Buscar orden por ID", description = "Busca y recupera una orden de trabajo específica basándose en su clave primaria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada y retornada correctamente"),
            @ApiResponse(responseCode = "404", description = "Not Found: El ID de orden proporcionado no existe")
    })

    // Endpoint para buscar una orden única por ID (GET /api/ordenes/1)
    @GetMapping("/{id}")
    public ResponseEntity<OrdenTrabajo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenTrabajoService.obtenerPorId(id));
    }




    // Documentar el endpoint para buscar órdenes de una patente
    @Operation(summary = "Buscar órdenes de una patente", description = "Recupera la lista de todas las órdenes asociadas a un vehículo histórico mediante su patente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial técnico del vehículo recuperado con éxito"),
            @ApiResponse(responseCode = "204", description = "No Content: El vehículo existe pero no cuenta con órdenes anteriores en el taller")
    })

    // Endpoint para buscar órdenes de una patente (GET /api/ordenes/vehiculo/BJKL-45)
    @GetMapping("/vehiculo/{patente}")
    public ResponseEntity<List<OrdenTrabajo>> buscarPorPatente(@PathVariable String patente) {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerPorPatente(patente);
        if (ordenes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si ese auto no tiene historial
        }
        return ResponseEntity.ok(ordenes); // 200 con la lista de órdenes del auto
    }




    // Documentar el endpoint para actualizar el estado
    @Operation(summary = "Actualizar el estado de la orden", description = "Permite mutar o cambiar parcialmente el estado de la orden (por ejemplo, de 'INGRESADO' a 'TERMINADO') usando parámetros de consulta REST.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado modificado correctamente en la base de datos"),
            @ApiResponse(responseCode = "404", description = "Not Found: La orden que se intenta modificar no existe en el sistema")
    })

    // Endpoint para actualizar el estado (PATCH /api/ordenes/1/estado?nuevo=EN_PROCESO)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenTrabajo> cambiarEstado(@PathVariable Long id, @RequestParam String nuevo) {
        OrdenTrabajo ordenActualizada = ordenTrabajoService.actualizarEstadoOrden(id, nuevo);
        return ResponseEntity.ok(ordenActualizada);
    }
























}


