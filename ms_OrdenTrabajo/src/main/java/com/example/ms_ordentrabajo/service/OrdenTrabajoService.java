package com.example.ms_ordentrabajo.service;

import com.example.ms_ordentrabajo.client.MecanicoFeignClient;
import com.example.ms_ordentrabajo.client.VehiculoFeignClient;
import com.example.ms_ordentrabajo.dto.OrdenRegistroDTO;
import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.model.Repuesto;
import com.example.ms_ordentrabajo.repository.OrdenTrabajoRepository;
import com.example.ms_ordentrabajo.repository.RepuestoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class OrdenTrabajoService {


    //Creacion de objetos de Repository y FeingClient

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private VehiculoFeignClient vehiculoFeignClient;

    @Autowired
    private MecanicoFeignClient mecanicoFeignClient;





    // Registrar una orden de Trabajo
    public OrdenTrabajo registrarOrdenCompleta(OrdenRegistroDTO registro) {
        OrdenTrabajo orden = registro.getOrden();
        String patenteFormateada = orden.getPatenteVehiculo().toUpperCase();

        log.info("Empezando a registrar una nueva orden de trabajo.");

        // Validaciones con OpenFeign
        try {
            log.info("Revisando si la patente existe en el microservicio de autos...");
            vehiculoFeignClient.obtenerVehiculoPorPatente(patenteFormateada);
            log.info("Auto verificado con éxito.");
        } catch (Exception e) {
            log.error("Error: El servicio de autos dice que la patente {} no existe.", patenteFormateada);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El vehículo con patente " + orden.getPatenteVehiculo() + " no existe.");
        }

        try {
            log.info("Revisando si el mecánico con ID {} existe...", orden.getMecanicoId());
            mecanicoFeignClient.obtenerMecanicoPorId(orden.getMecanicoId());
            log.info("Mecánico verificado con éxito.");
        } catch (Exception e) {
            log.error("Error: El mecánico no existe en el sistema.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El mecánico con ID " + orden.getMecanicoId() + " no existe.");
        }

        // Cálculo automático del costo estimado en base a los repuestos
        double total = 0.0;
        if (registro.getRepuestos() != null) {
            log.info("Calculando el costo total de los repuestos añadidos");
            for (Repuesto rep : registro.getRepuestos()) {
                total += rep.getPrecioUnitario() * rep.getCantidad();
            }
            log.info("Costo total de repuestos calculado: ${}", total);
        } else {
            log.warn("Nota: Esta orden se va a registrar sin repuestos.");
        }

        //Datos guardados de la orden
        orden.setFechaIngreso(LocalDate.now());
        orden.setCostoEstimado(total);
        orden.setPatenteVehiculo(patenteFormateada);


        OrdenTrabajo ordenGuardada = ordenTrabajoRepository.save(orden);
        log.info("Orden guardada con ID: {}", ordenGuardada.getId());

        // Guardado de repuestos enlazados al ID de la orden
        if (registro.getRepuestos() != null) {
            for (Repuesto rep : registro.getRepuestos()) {
                rep.setOrdenId(ordenGuardada.getId());
                repuestoRepository.save(rep);
            }
            log.info("Repuestos guardados correctamente.");
        }

        log.info("La orden  se creó con éxito.");
        return ordenGuardada;
    }

    // Obtener todas las ordenes de trabajo
    public List<OrdenTrabajo> obtenerTodas() {
        log.info("Buscando el listado completo de órdenes de trabajo.");
        List<OrdenTrabajo> lista = ordenTrabajoRepository.findAll();
        log.info("Se encontraron las ordenes");
        return lista;
    }

    // Buscar una orden específica por su ID
    public OrdenTrabajo obtenerPorId(Long id) {
        log.info("Buscando la orden de trabajo con ID: {}", id);
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró ninguna orden con el ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "La orden ID " + id + " no existe.");
                });
    }

    // Traer el historial de órdenes de una patente
    public List<OrdenTrabajo> obtenerPorPatente(String patente) {
        log.info("Buscando órdenes asociadas a la patente: {}", patente);
        List<OrdenTrabajo> ordenes = ordenTrabajoRepository.findByPatenteVehiculoIgnoreCase(patente);
        log.info("Para la patente {} encontramos {} órdenes anteriores.", patente, ordenes.size());
        return ordenes;
    }

    // Cambiar el estado de la orden (INGRESADO a TERMINADO)
    @Transactional
    public OrdenTrabajo actualizarEstadoOrden(Long id, String nuevoEstado) {
        log.info("Preparando cambio de estado para la orden número: {}", id);

        OrdenTrabajo orden = obtenerPorId(id);
        String estadoAnterior = orden.getEstado();
        orden.setEstado(nuevoEstado.toUpperCase());

        log.info("Cambiando el estado");
        OrdenTrabajo ordenActualizada = ordenTrabajoRepository.save(orden);

        log.info("El estado de la orden se actualizó correctamente en la base de datos.");
        return ordenActualizada;
    }
}









