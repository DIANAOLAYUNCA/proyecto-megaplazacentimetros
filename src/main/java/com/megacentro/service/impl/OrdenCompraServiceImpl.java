package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.OrdenCompraRepository;
import com.megacentro.service.OrdenCompraService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraServiceImpl extends AbstractCrudService<OrdenCompra, Integer> implements OrdenCompraService {

    private final OrdenCompraRepository repository;

    public OrdenCompraServiceImpl(OrdenCompraRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<OrdenCompra, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "OrdenCompra";
    }

    @Override
    protected void antesDeRegistrar(OrdenCompra e) {
        e.setIdOrdenCompra(null);
    }

    @Override
    protected void asignarId(OrdenCompra e, Integer id) {
        e.setIdOrdenCompra(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(OrdenCompra e) {
        e.setEstado("ANULADA");
    }

    @Override
    protected void marcarActivo(OrdenCompra e) {
        e.setEstado("EMITIDA");
    }

    @Override
    public List<OrdenCompra> listarActivos() {
        return repository.findByEstadoNot("ANULADA");
    }

}
