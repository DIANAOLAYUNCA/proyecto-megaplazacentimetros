package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.EmpleadoRepository;
import com.megacentro.service.EmpleadoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl extends AbstractCrudService<Empleado, Integer> implements EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoServiceImpl(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Empleado, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Empleado";
    }

    @Override
    protected void antesDeRegistrar(Empleado e) {
        e.setIdEmpleado(null);
    }

    @Override
    protected void asignarId(Empleado e, Integer id) {
        e.setIdEmpleado(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Empleado e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(Empleado e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Empleado> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
