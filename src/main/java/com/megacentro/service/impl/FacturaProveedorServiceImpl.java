package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.FacturaProveedorRepository;
import com.megacentro.service.FacturaProveedorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaProveedorServiceImpl extends AbstractCrudService<FacturaProveedor, Integer> implements FacturaProveedorService {

    private final FacturaProveedorRepository repository;

    public FacturaProveedorServiceImpl(FacturaProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<FacturaProveedor, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "FacturaProveedor";
    }

    @Override
    protected void antesDeRegistrar(FacturaProveedor e) {
        e.setIdFacturaProveedor(null);
    }

    @Override
    protected void asignarId(FacturaProveedor e, Integer id) {
        e.setIdFacturaProveedor(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(FacturaProveedor e) {
        e.setEstado("ANULADA");
    }

    @Override
    protected void marcarActivo(FacturaProveedor e) {
        e.setEstado("PENDIENTE");
    }

    @Override
    public List<FacturaProveedor> listarActivos() {
        return repository.findByEstadoNot("ANULADA");
    }

}
