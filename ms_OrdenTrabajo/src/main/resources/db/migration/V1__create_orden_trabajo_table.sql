CREATE TABLE orden_trabajo (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               fecha_ingreso DATE NOT NULL,
                               descripcion_falla VARCHAR(255) NOT NULL,
                               estado VARCHAR(20) NOT NULL,
                               costo_estimado DECIMAL(10, 2) NOT NULL,
                               mecanico_id BIGINT NOT NULL,
                               patente_vehiculo VARCHAR(10) NOT NULL
);


