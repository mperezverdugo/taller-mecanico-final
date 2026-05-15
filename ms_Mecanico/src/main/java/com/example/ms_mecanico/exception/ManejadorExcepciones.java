package com.example.ms_mecanico.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class ManejadorExcepciones {


    //Manejo de expceciones manuales

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeNegocio(ResponseStatusException ex){
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("error", ex.getReason());
        return new ResponseEntity<>(respuesta, ex.getStatusCode());

    }



    //Manejo de expcepciones por validaciones






}
