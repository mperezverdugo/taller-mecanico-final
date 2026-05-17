package com.example.ms_ordentrabajo.repository;

import com.example.ms_ordentrabajo.model.OrdenTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenTrabajoRepository extends JpaRepository<OrdenTrabajo, Long> {


    List<OrdenTrabajo> findByPatenteVehiculoIgnoreCase(String patenteVehiculo);
}