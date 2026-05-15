CREATE TABLE mecanicos (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           rut VARCHAR(12) NOT NULL UNIQUE,
                           nombre VARCHAR(50) NOT NULL,
                           especialidad VARCHAR(50) NOT NULL,
                           estado VARCHAR(20) NOT NULL
);



