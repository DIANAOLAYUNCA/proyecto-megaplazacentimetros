package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.CapacitacionRepository;
import com.megacentro.service.CapacitacionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacitacionServiceImpl extends AbstractCrudService<Capacitacion, Integer> implements CapacitacionService {

    private final CapacitacionRepository repository;

    public CapacitacionServiceImpl(CapacitacionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Capacitacion, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Capacitacion";
    }

    @Override
    protected void antesDeRegistrar(Capacitacion e) {
        e.setIdCapacitacion(null);
    }

    @Override
    protected void asignarId(Capacitacion e, Integer id) {
        e.setIdCapacitacion(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Capacitacion e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Capacitacion e) {
        e.setActivo(true);
    }

    @Override
    public List<Capacitacion> listarActivos() {
        return repository.findByActivoTrue();
    }

}
