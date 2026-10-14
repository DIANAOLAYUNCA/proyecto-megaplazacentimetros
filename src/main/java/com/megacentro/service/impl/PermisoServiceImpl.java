package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PermisoRepository;
import com.megacentro.service.PermisoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisoServiceImpl extends AbstractCrudService<Permiso, Integer> implements PermisoService {

    private final PermisoRepository repository;

    public PermisoServiceImpl(PermisoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Permiso, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Permiso";
    }

    @Override
    protected void antesDeRegistrar(Permiso e) {
        e.setIdPermiso(null);
    }

    @Override
    protected void asignarId(Permiso e, Integer id) {
        e.setIdPermiso(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Permiso e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Permiso e) {
        e.setActivo(true);
    }

    @Override
    public List<Permiso> listarActivos() {
        return repository.findByActivoTrue();
    }

}
