CREATE TABLE vehiculos (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           patente VARCHAR(10) NOT NULL UNIQUE,
                           marca VARCHAR(50) NOT NULL,
                           modelo VARCHAR(50) NOT NULL,
                           anio INT NOT NULL,
                           cliente_id BIGINT NOT NULL
);

