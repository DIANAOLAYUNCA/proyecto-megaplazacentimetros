package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.RolRepository;
import com.megacentro.service.RolService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl extends AbstractCrudService<Rol, Integer> implements RolService {

    private final RolRepository repository;

    public RolServiceImpl(RolRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Rol, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Rol";
    }

    @Override
    protected void antesDeRegistrar(Rol e) {
        e.setIdRol(null);
    }

    @Override
    protected void asignarId(Rol e, Integer id) {
        e.setIdRol(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Rol e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(Rol e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Rol> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
