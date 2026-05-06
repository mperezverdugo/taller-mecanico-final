package com.example.ms_Cliente.exception;

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

    // Radar para errores que nosotros lanzamos manualmente (ej: 404 No Encontrado)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeNegocio(ResponseStatusException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getReason());
        return new ResponseEntity<>(respuesta, ex.getStatusCode());
    }

    // Radar para errores de validación (ej: @NotBlank, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", "Datos invalidos: Revisa los campos enviados.");
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}