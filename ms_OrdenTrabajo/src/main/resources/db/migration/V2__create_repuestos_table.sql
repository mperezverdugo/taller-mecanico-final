CREATE TABLE repuestos (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           cantidad INT NOT NULL,
                           precio_unitario DECIMAL(10, 2) NOT NULL,
                           orden_id BIGINT NOT NULL
);