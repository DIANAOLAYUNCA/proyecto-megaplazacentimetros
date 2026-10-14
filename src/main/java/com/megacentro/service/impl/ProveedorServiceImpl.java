package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.ProveedorRepository;
import com.megacentro.service.ProveedorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl extends AbstractCrudService<Proveedor, Integer> implements ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorServiceImpl(ProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Proveedor, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Proveedor";
    }

    @Override
    protected void antesDeRegistrar(Proveedor e) {
        e.setIdProveedor(null);
    }

    @Override
    protected void asignarId(Proveedor e, Integer id) {
        e.setIdProveedor(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Proveedor e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(Proveedor e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Proveedor> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
