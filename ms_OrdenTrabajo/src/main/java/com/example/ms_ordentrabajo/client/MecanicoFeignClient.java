package com.example.ms_ordentrabajo.client;

import com.example.ms_ordentrabajo.dto.MecanicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-mecanico")
public interface MecanicoFeignClient {



    @GetMapping("/api/mecanicos/{id}")
    MecanicoDTO obtenerMecanicoPorId(@PathVariable("id") Long id);
}