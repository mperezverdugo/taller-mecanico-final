package com.example.ms_mecanico.service;

import com.example.ms_mecanico.model.Mecanico;
import com.example.ms_mecanico.repository.MecanicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;


    //Buscar a un Mecanico por su ID
    public Mecanico buscarPorId(Long id) {
        return mecanicoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Error: Mecanico con ID {} no encontrado", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Mecanico no encontrado en el sistema.");
                });
    }



    // Obtener todos los Mecanicos
    public List<Mecanico> obtenerTodos(){
        log.info("Obteniendo una lista de todos los mecanicos");
        return mecanicoRepository.findAll();
    }


    public void eliminar(Long id) {
        Mecanico mecanico = buscarPorId(id); // Validamos que exista antes de intentar borrar
        mecanicoRepository.delete(mecanico);
        log.warn("Mecanico con ID {} eliminado correctamente", id);

    }


    //Actualizar

    @Transactional
    public Mecanico actualizar(Long id, Mecanico mecanicoActualizado){

        Mecanico mecanicoExistente = buscarPorId(id);
        log.info("Actualizando datos del Mecanico con ID: {}", id);

        mecanicoExistente.setRut(mecanicoActualizado.getRut());
        mecanicoExistente.setNombre(mecanicoActualizado.getNombre());
        mecanicoExistente.setEspecialidad(mecanicoActualizado.getEspecialidad());
        mecanicoExistente.setEstado(mecanicoActualizado.getEstado());

        log.warn("Actualizacion del mecanico con id {} completado", id);
        return mecanicoRepository.save(mecanicoExistente);
    }



    //Actualizar

    @Transactional
    public Mecanico guardar(Mecanico nuevoMecanico) {
        log.info("Guardando nuevo mecanico con RUT: {}", nuevoMecanico.getRut());
        return mecanicoRepository.save(nuevoMecanico);
    }











}


