package com.example.ms_ordentrabajo.client;

import com.example.ms_ordentrabajo.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-automovil")
public interface VehiculoFeignClient {


    @GetMapping("/api/vehiculos/patente/{patente}")
    VehiculoDTO obtenerVehiculoPorPatente(@PathVariable("patente") String patente);
}