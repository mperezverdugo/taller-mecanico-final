package com.example.ms_ordentrabajo.service;

import com.example.ms_ordentrabajo.client.MecanicoFeignClient;
import com.example.ms_ordentrabajo.client.VehiculoFeignClient;
import com.example.ms_ordentrabajo.dto.OrdenRegistroDTO;
import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import com.example.ms_ordentrabajo.model.Repuesto;
import com.example.ms_ordentrabajo.repository.OrdenTrabajoRepository;
import com.example.ms_ordentrabajo.repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenTrabajoService {


    //LLamar objetos de otras clases

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private VehiculoFeignClient vehiculoFeignClient;

    @Autowired
    private MecanicoFeignClient mecanicoFeignClient;




    public OrdenTrabajo registrarOrdenCompleta(OrdenRegistroDTO registro) {
        OrdenTrabajo orden = registro.getOrden();

        // 1. Validaciones con OpenFeign
        try {
            vehiculoFeignClient.obtenerVehiculoPorPatente(orden.getPatenteVehiculo().toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El vehículo con patente " + orden.getPatenteVehiculo() + " no existe.");
        }

        try {
            mecanicoFeignClient.obtenerMecanicoPorId(orden.getMecanicoId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El mecánico con ID " + orden.getMecanicoId() + " no existe.");
        }

        // 2. Cálculo automático del costo estimado en base a los repuestos
        double total = 0.0;
        if (registro.getRepuestos() != null) {
            for (Repuesto rep : registro.getRepuestos()) {
                total += rep.getPrecioUnitario() * rep.getCantidad();
            }
        }

        // 3. Seteo de auditoría y guardado de la Orden
        orden.setFechaIngreso(LocalDate.now());
        orden.setCostoEstimado(total);
        orden.setPatenteVehiculo(orden.getPatenteVehiculo().toUpperCase());

        OrdenTrabajo ordenGuardada = ordenTrabajoRepository.save(orden);

        // 4. Guardado de repuestos enlazados al ID de la orden
        if (registro.getRepuestos() != null) {
            for (Repuesto rep : registro.getRepuestos()) {
                rep.setOrdenId(ordenGuardada.getId());
                repuestoRepository.save(rep);
            }
        }

        return ordenGuardada;
    }




    //Obtener todas las ordenes de trabajo
    public List<OrdenTrabajo> obtenerTodas() {
        return ordenTrabajoRepository.findAll();
    }
}