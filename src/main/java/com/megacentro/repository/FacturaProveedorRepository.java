package com.megacentro.repository;

import com.megacentro.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, Integer> {

    /** Registros cuyo estado no es el de baja lógica. */
    List<FacturaProveedor> findByEstadoNot(String estado);
}
