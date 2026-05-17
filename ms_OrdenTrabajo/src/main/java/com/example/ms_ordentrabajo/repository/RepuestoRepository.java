package com.example.ms_ordentrabajo.repository;

import com.example.ms_ordentrabajo.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

}