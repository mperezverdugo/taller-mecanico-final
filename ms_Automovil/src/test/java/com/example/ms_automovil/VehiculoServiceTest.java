package com.example.ms_automovil;

import com.example.ms_automovil.client.ClienteFeignClient;
import com.example.ms_automovil.dto.ClienteDTO;
import com.example.ms_automovil.model.Vehiculo;
import com.example.ms_automovil.repository.VehiculoRepository;
import com.example.ms_automovil.service.VehiculoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ClienteFeignClient clienteFeignClient;

    @InjectMocks
    private VehiculoService vehiculoService;

    // ESCENARIO 1: GUARDAR AUTO EXITOSAMENTE (Usa Feign simulado)
    @Test
    public void cuandoGuardarVehiculoNuevo_entoncesRetornaVehiculoRegistrado() {
        // Arrange
        Vehiculo nuevoAuto = new Vehiculo(null, "BBDT40", "Toyota", "Yaris", 2015, 1L);
        Vehiculo autoGuardado = new Vehiculo(1L, "BBDT40", "Toyota", "Yaris", 2015, 1L);
        ClienteDTO clienteSimulado = new ClienteDTO(); // Objeto DTO vacío simulando que el cliente existe

        // Simulamos que no hay duplicados de patente
        Mockito.when(vehiculoRepository.findByPatente("BBDT40")).thenReturn(null);
        // Simulamos que OpenFeign encuentra al cliente dueño en el ms_Cliente
        Mockito.when(clienteFeignClient.obtenerClientePorId(1L)).thenReturn(clienteSimulado);
        // Simulamos el guardado en repositorio
        Mockito.when(vehiculoRepository.save(Mockito.any(Vehiculo.class))).thenReturn(autoGuardado);

        // Act
        Vehiculo resultado = vehiculoService.guardar(nuevoAuto);

        // Assert
        assertNotNull(resultado);
        assertEquals("BBDT40", resultado.getPatente());
        assertEquals(1L, resultado.getId());
    }

    // ESCENARIO 2: ERROR CUANDO LA PATENTE YA EXISTE
    @Test
    public void cuandoGuardarPatenteDuplicada_entoncesLanzaBadRequest() {
        // Arrange
        Vehiculo autoDuplicado = new Vehiculo(null, "BBDT40", "Toyota", "Yaris", 2015, 1L);
        Vehiculo autoExistenteEnBD = new Vehiculo(1L, "BBDT40", "Toyota", "Yaris", 2015, 1L);

        Mockito.when(vehiculoRepository.findByPatente("BBDT40")).thenReturn(autoExistenteEnBD);

        // Act & Assert
        ResponseStatusException excepcion = assertThrows(ResponseStatusException.class, () -> {
            vehiculoService.guardar(autoDuplicado);
        });

        assertEquals(HttpStatus.BAD_REQUEST, excepcion.getStatusCode());
        assertEquals("La patente ya está registrada.", excepcion.getReason());
    }
}