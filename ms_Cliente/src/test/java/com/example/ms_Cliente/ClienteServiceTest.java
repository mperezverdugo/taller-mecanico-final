package com.example.ms_Cliente;

import com.example.ms_Cliente.model.Cliente;
import com.example.ms_Cliente.repository.ClienteRepository;
import com.example.ms_Cliente.service.ClienteService;
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

// 1. Se activa Mockito para poder interceptar llamadas y crear objetos falsos
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    // 2. Se Crea un "clon falso" del repositorio.
    @Mock
    private ClienteRepository clienteRepository;

    // 3. Se Inyecta ese clon falso dentro del servicio real para engañarlo en la prueba
    @InjectMocks
    private ClienteService clienteService;

    // ===================================================================
    // PRUEBA 1:  GUARDAR
    // ===================================================================
    @Test
    public void cuandoGuardarCliente_entoncesRetornaClienteRegistrado() {
        // ARRANGE (datos del modelo)
        Cliente clienteNuevo = new Cliente(null, "12.221.244-0", "Pedro Lopez", "9654385", "pedro@duocuc.cl");
        Cliente clienteGuardado = new Cliente(1L, "12.221.244-0", "Pedro Lopez", "9654385", "pedro@duocuc.cl");

        // Se le dice al repositorio simulado qué responder cuando tu servicio lo llame
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteGuardado);

        // ACT (Ejecutar tu método real del servicio)
        Cliente resultado = clienteService.guardar(clienteNuevo);

        // ASSERT (Verificar que los datos coincidan usando JUnit)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pedro Lopez", resultado.getNombre());
    }

    // ===================================================================
    // PRUEBA 2: MANEJO DE ERROR (BUSCAR ID QUE NO EXISTE)
    // ===================================================================
    @Test
    public void cuandoBuscarPorIdInexistente_entoncesLanzaResponseStatusException() {
        // ARRANGE: Simulamos que al buscar el ID 99, el repositorio devuelve un vacío (Optional.empty())
        Long idInexistente = 99L;
        Mockito.when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // ACT & ASSERT: Verificamos que tu código lance la excepción de Spring (ResponseStatusException)
        ResponseStatusException excepcion = assertThrows(ResponseStatusException.class, () -> {
            clienteService.buscarPorId(idInexistente);
        });

        // Validamos que el mensaje y el código HTTP sean idénticos a los que tú escribiste
        assertEquals(HttpStatus.NOT_FOUND, excepcion.getStatusCode());
        assertEquals("Cliente no encontrado en el sistema.", excepcion.getReason());
    }

    // ===================================================================
    // PRUEBA 3: LOGICA DE ACTUALIZAR
    // ===================================================================
    @Test
    public void cuandoActualizarClienteExistente_entoncesModificaCamposCorrectamente() {
        // ARRANGE: Simulamos que ya existe un cliente con datos viejos en la base de datos
        Long idExistente = 1L;
        Cliente clienteViejo = new Cliente(idExistente, "12.221.244-0", "Pedro Lopez", "9654385", "pedro@duocuc.cl");
        Cliente datosNuevos = new Cliente(null, "12.221.244-0", "Pedro Modificado", "9999999", "pedronuevo@duocuc.cl");

        // Tu método 'actualizar' primero busca por ID y luego guarda. Simulamos ambos pasos:
        Mockito.when(clienteRepository.findById(idExistente)).thenReturn(Optional.of(clienteViejo));
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteViejo);

        // ACT: Ejecutamos tu método actualizar real
        Cliente resultado = clienteService.actualizar(idExistente, datosNuevos);

        // ASSERT: Validamos que los setters cambiaron los valores viejos por los nuevos
        assertNotNull(resultado);
        assertEquals("Pedro Modificado", resultado.getNombre());
        assertEquals("9999999", resultado.getTelefono());
    }
}