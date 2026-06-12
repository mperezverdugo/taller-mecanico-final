package com.example.ms_automovil.client;

import com.example.ms_automovil.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente")
public interface ClienteFeignClient {



    @GetMapping("/api/clientes/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable("id") Long id);
}


