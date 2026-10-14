package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PagoProveedorRepository;
import com.megacentro.service.PagoProveedorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoProveedorServiceImpl extends AbstractCrudService<PagoProveedor, Integer> implements PagoProveedorService {

    private final PagoProveedorRepository repository;

    public PagoProveedorServiceImpl(PagoProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<PagoProveedor, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "PagoProveedor";
    }

    @Override
    protected void antesDeRegistrar(PagoProveedor e) {
        e.setIdPagoProveedor(null);
    }

    @Override
    protected void asignarId(PagoProveedor e, Integer id) {
        e.setIdPagoProveedor(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(PagoProveedor e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(PagoProveedor e) {
        e.setActivo(true);
    }

    @Override
    public List<PagoProveedor> listarActivos() {
        return repository.findByActivoTrue();
    }

}
