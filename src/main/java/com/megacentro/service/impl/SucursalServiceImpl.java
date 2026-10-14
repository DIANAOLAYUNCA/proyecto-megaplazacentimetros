package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.SucursalRepository;
import com.megacentro.service.SucursalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalServiceImpl extends AbstractCrudService<Sucursal, Integer> implements SucursalService {

    private final SucursalRepository repository;

    public SucursalServiceImpl(SucursalRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Sucursal, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Sucursal";
    }

    @Override
    protected void antesDeRegistrar(Sucursal e) {
        e.setIdSucursal(null);
    }

    @Override
    protected void asignarId(Sucursal e, Integer id) {
        e.setIdSucursal(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Sucursal e) {
        e.setEstado("INACTIVA");
    }

    @Override
    protected void marcarActivo(Sucursal e) {
        e.setEstado("ACTIVA");
    }

    @Override
    public List<Sucursal> listarActivos() {
        return repository.findByEstadoNot("INACTIVA");
    }

}
