package com.example.ms_ordentrabajo;

import com.example.ms_ordentrabajo.client.MecanicoFeignClient;
import com.example.ms_ordentrabajo.client.VehiculoFeignClient;
import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.repository.OrdenTrabajoRepository;
import com.example.ms_ordentrabajo.service.OrdenTrabajoService;
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
public class OrdenTrabajoServiceTest {

    @Mock
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Mock
    private VehiculoFeignClient vehiculoFeignClient;

    @Mock
    private MecanicoFeignClient mecanicoFeignClient;

    @InjectMocks
    private OrdenTrabajoService ordenTrabajoService;

    // ESCENARIO 1:  OBTENER ORDEN POR ID
    @Test
    public void cuandoObtenerPorIdExistente_entoncesRetornaOrden() {
        // Arrange
        OrdenTrabajo ordenSimulada = new OrdenTrabajo();
        ordenSimulada.setId(1L);
        ordenSimulada.setPatenteVehiculo("BBDT40");
        ordenSimulada.setDescripcionFalla("Cambio de pastillas de freno");
        ordenSimulada.setEstado("INGRESADO");
        ordenSimulada.setCostoEstimado(45000.0);
        ordenSimulada.setMecanicoId(2L);

        Mockito.when(ordenTrabajoRepository.findById(1L)).thenReturn(Optional.of(ordenSimulada));

        // Act
        OrdenTrabajo resultado = ordenTrabajoService.obtenerPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("INGRESADO", resultado.getEstado());
        assertEquals("Cambio de pastillas de freno", resultado.getDescripcionFalla());
    }

    // ESCENARIO 2: ACTUALIZAR EL ESTADO DE LA ORDEN DE TRABAJO
    @Test
    public void cuandoActualizarEstadoOrden_entoncesModificaEstadoAUpperCase() {
        // Arrange
        OrdenTrabajo ordenOriginal = new OrdenTrabajo();
        ordenOriginal.setId(5L);
        ordenOriginal.setEstado("INGRESADO");
        ordenOriginal.setPatenteVehiculo("BBDT40");
        ordenOriginal.setDescripcionFalla("Alineacion");
        ordenOriginal.setCostoEstimado(20000.0);

        Mockito.when(ordenTrabajoRepository.findById(5L)).thenReturn(Optional.of(ordenOriginal));
        Mockito.when(ordenTrabajoRepository.save(Mockito.any(OrdenTrabajo.class))).thenReturn(ordenOriginal);

        // Act
        OrdenTrabajo resultado = ordenTrabajoService.actualizarEstadoOrden(5L, "terminado");

        // Assert
        assertNotNull(resultado);
        assertEquals("TERMINADO", resultado.getEstado());
    }
}