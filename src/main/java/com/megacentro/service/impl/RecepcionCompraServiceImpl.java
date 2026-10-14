package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.RecepcionCompraRepository;
import com.megacentro.service.RecepcionCompraService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecepcionCompraServiceImpl extends AbstractCrudService<RecepcionCompra, Integer> implements RecepcionCompraService {

    private final RecepcionCompraRepository repository;

    public RecepcionCompraServiceImpl(RecepcionCompraRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<RecepcionCompra, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "RecepcionCompra";
    }

    @Override
    protected void antesDeRegistrar(RecepcionCompra e) {
        e.setIdRecepcion(null);
    }

    @Override
    protected void asignarId(RecepcionCompra e, Integer id) {
        e.setIdRecepcion(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(RecepcionCompra e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(RecepcionCompra e) {
        e.setActivo(true);
    }

    @Override
    public List<RecepcionCompra> listarActivos() {
        return repository.findByActivoTrue();
    }

}
