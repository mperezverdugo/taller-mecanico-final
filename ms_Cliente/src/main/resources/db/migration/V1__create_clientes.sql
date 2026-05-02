-- V1__create_clientes.sql
CREATE TABLE clientes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          rut VARCHAR(12) NOT NULL UNIQUE,
                          nombre VARCHAR(100) NOT NULL,
                          correo VARCHAR(100),
                          telefono VARCHAR(20)
);