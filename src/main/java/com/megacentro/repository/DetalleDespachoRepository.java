package com.megacentro.repository;

import com.megacentro.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleDespachoRepository extends JpaRepository<DetalleDespacho, DetalleDespachoId> {

    List<DetalleDespacho> findByActivoTrue();
}
