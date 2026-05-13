package com.example.ms_automovil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorExcepciones {

    // Captura errores manuales (ej: lanzar un 404 si la patente no existe)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeNegocio(ResponseStatusException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getReason());
        return new ResponseEntity<>(respuesta, ex.getStatusCode());
    }

    // Captura errores de validación de campos (ej: @NotNull, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Datos del vehículo inválidos: Revisa los campos enviados.");
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}