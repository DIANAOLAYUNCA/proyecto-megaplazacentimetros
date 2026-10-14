package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.EmpleadoCapacitacionRepository;
import com.megacentro.service.EmpleadoCapacitacionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoCapacitacionServiceImpl extends AbstractCrudService<EmpleadoCapacitacion, EmpleadoCapacitacionId> implements EmpleadoCapacitacionService {

    private final EmpleadoCapacitacionRepository repository;

    public EmpleadoCapacitacionServiceImpl(EmpleadoCapacitacionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<EmpleadoCapacitacion, EmpleadoCapacitacionId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "EmpleadoCapacitacion";
    }

    @Override
    protected void antesDeRegistrar(EmpleadoCapacitacion e) {
        if (e.getEmpleado() == null || e.getEmpleado().getIdEmpleado() == null
                || e.getCapacitacion() == null || e.getCapacitacion().getIdCapacitacion() == null) {
            throw new IllegalArgumentException("Debe enviar empleado.idEmpleado y capacitacion.idCapacitacion");
        }
        e.setId(new EmpleadoCapacitacionId(e.getEmpleado().getIdEmpleado(), e.getCapacitacion().getIdCapacitacion()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de EmpleadoCapacitacion con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(EmpleadoCapacitacion e, EmpleadoCapacitacionId id) {
        e.setId(id);
        Empleado a = new Empleado();
        a.setIdEmpleado(id.getIdEmpleado());
        e.setEmpleado(a);
        Capacitacion b = new Capacitacion();
        b.setIdCapacitacion(id.getIdCapacitacion());
        e.setCapacitacion(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(EmpleadoCapacitacion e) {
        e.setEstado("RETIRADO");
    }

    @Override
    protected void marcarActivo(EmpleadoCapacitacion e) {
        e.setEstado("INSCRITO");
    }

    @Override
    public List<EmpleadoCapacitacion> listarActivos() {
        return repository.findByEstadoNot("RETIRADO");
    }

}
