package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DespachoRepository;
import com.megacentro.service.DespachoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespachoServiceImpl extends AbstractCrudService<Despacho, Integer> implements DespachoService {

    private final DespachoRepository repository;

    public DespachoServiceImpl(DespachoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Despacho, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Despacho";
    }

    @Override
    protected void antesDeRegistrar(Despacho e) {
        e.setIdDespacho(null);
    }

    @Override
    protected void asignarId(Despacho e, Integer id) {
        e.setIdDespacho(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Despacho e) {
        e.setEstado("ANULADO");
    }

    @Override
    protected void marcarActivo(Despacho e) {
        e.setEstado("PREPARACION");
    }

    @Override
    public List<Despacho> listarActivos() {
        return repository.findByEstadoNot("ANULADO");
    }

}
