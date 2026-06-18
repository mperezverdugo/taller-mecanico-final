package com.example.ms_Cliente.controller;

import com.example.ms_Cliente.model.Cliente;
import com.example.ms_Cliente.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

@Tag(name = "Módulo Clientes", description = "Operaciones CRUD para la gestión de clientes del taller mecánico")
public class ClienteController {


    //Creacion del objeto que viene del Service

    private final ClienteService clienteService;


    // 1. DOCUMENTAR EL LISTAR
    @Operation(summary = "Listar todos los clientes", description = "Retorna una lista completa de clientes. Si no existen registros en el sistema, devuelve un código HTTP 204.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes recuperado con éxito"),
            @ApiResponse(responseCode = "204", description = "No Content: No existen clientes registrados en la base de datos")
    })

    //Listar

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        List<Cliente> clientes = clienteService.obtenerTodos();

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 - No hay clientes
        }

        return ResponseEntity.ok(clientes); // 200 - Lista de clientes
    }

    // 2. DOCUMENTAR EL CREAR
    @Operation(summary = "Crear un nuevo cliente", description = "Permite registrar un cliente validando los campos obligatorios mediante validación estricta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Error en las validaciones de entrada o RUT duplicado")
    })

    //Crear

    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        // Código 201 Created para creaciones exitosas
        return ResponseEntity.status(201).body(clienteService.guardar(cliente));
    }


    // 3. DOCUMENTAR EL BUSCAR
    @Operation(summary = "Buscar un cliente por ID", description = "Busca y recupera un registro único de cliente basado en su identificador primario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado y retornado con éxito"),
            @ApiResponse(responseCode = "404", description = "Not Found: No existe ningún cliente con el ID proporcionado")
    })


    //Buscar

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        // El Service se encarga de lanzar el 404 si no existe
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }





    // 4. DOCUMENTAR EL ELIMINAR
    @Operation(summary = "Eliminar un cliente", description = "Remueve físicamente el registro de un cliente del taller mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "Not Found: Intento de eliminar un cliente que no existe")
    })

    //Eliminar

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



    // 5. DOCUMENTAR EL ACTUALIZAR
    @Operation(summary = "Actualizar datos de un cliente", description = "Reemplaza y actualiza la información de un cliente existente localizándolo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Datos inválidos proporcionados en el cuerpo de la petición"),
            @ApiResponse(responseCode = "404", description = "Not Found: El cliente con el ID especificado no fue encontrado")
    })

    //Actualizar

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        // El Service tiene la logica  y retorna el cliente actualizado con HTTP 200.
        Cliente clienteActualizado = clienteService.actualizar(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }
}
