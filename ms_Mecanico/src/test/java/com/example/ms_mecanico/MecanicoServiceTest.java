package com.example.ms_mecanico;

import com.example.ms_mecanico.model.Mecanico;
import com.example.ms_mecanico.repository.MecanicoRepository;
import com.example.ms_mecanico.service.MecanicoService;
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
public class MecanicoServiceTest {

    @Mock
    private MecanicoRepository mecanicoRepository;

    @InjectMocks
    private MecanicoService mecanicoService;

    // TEST 1: CAMINO FELIZ DE GUARDAR
    @Test
    public void cuandoGuardarMecanico_entoncesRetornaMecanicoRegistrado() {
        // Arrange - Seteamos los campos paso a paso
        Mecanico nuevoMecanico = new Mecanico();
        nuevoMecanico.setRut("12.345.678-9");
        nuevoMecanico.setNombre("Juan Perez");
        nuevoMecanico.setEspecialidad("Motor de Combustion");
        nuevoMecanico.setEstado("Disponible");

        Mecanico mecanicoGuardado = new Mecanico();
        mecanicoGuardado.setId(1L);
        mecanicoGuardado.setRut("12.345.678-9");
        mecanicoGuardado.setNombre("Juan Perez");
        mecanicoGuardado.setEspecialidad("Motor de Combustion");
        mecanicoGuardado.setEstado("Disponible");

        Mockito.when(mecanicoRepository.save(Mockito.any(Mecanico.class))).thenReturn(mecanicoGuardado);

        // Act
        Mecanico resultado = mecanicoService.guardar(nuevoMecanico);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    // TEST 2: ERROR CUANDO EL MECÁNICO NO EXISTE
    @Test
    public void cuandoBuscarMecanicoInexistente_entoncesLanzaResponseStatusException() {
        // Arrange
        Long idInexistente = 99L;
        Mockito.when(mecanicoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException excepcion = assertThrows(ResponseStatusException.class, () -> {
            mecanicoService.buscarPorId(idInexistente);
        });

        assertEquals(HttpStatus.NOT_FOUND, excepcion.getStatusCode());
        assertEquals("Mecanico no encontrado en el sistema.", excepcion.getReason());
    }

    // TEST 3: ACTUALIZAR LÓGICA DE CAMPOS
    @Test
    public void cuandoActualizarMecanicoExistente_entoncesModificaCamposCorrectamente() {
        // Arrange - Definimos el estado viejo
        Mecanico mecanicoViejo = new Mecanico();
        mecanicoViejo.setId(1L);
        mecanicoViejo.setRut("12.345.678-9");
        mecanicoViejo.setNombre("Juan Perez");
        mecanicoViejo.setEspecialidad("Motor de Combustion");
        mecanicoViejo.setEstado("Disponible");

        // Nuevos datos a inyectar
        Mecanico datosNuevos = new Mecanico();
        datosNuevos.setRut("12.345.678-9");
        datosNuevos.setNombre("Juan Perez Modificado");
        datosNuevos.setEspecialidad("Electricidad");
        datosNuevos.setEstado("Ocupado");

        Mockito.when(mecanicoRepository.findById(1L)).thenReturn(Optional.of(mecanicoViejo));
        Mockito.when(mecanicoRepository.save(Mockito.any(Mecanico.class))).thenReturn(mecanicoViejo);

        // Act
        Mecanico resultado = mecanicoService.actualizar(1L, datosNuevos);

        // Assert
        assertNotNull(resultado);
        assertEquals("Juan Perez Modificado", resultado.getNombre());
        assertEquals("Electricidad", resultado.getEspecialidad());
        assertEquals("Ocupado", resultado.getEstado());
    }
}