package com.example.ms_Cliente.exception;

// Esta clase hereda de RuntimeException para que Spring pueda manejarla en tiempo de ejecución
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}