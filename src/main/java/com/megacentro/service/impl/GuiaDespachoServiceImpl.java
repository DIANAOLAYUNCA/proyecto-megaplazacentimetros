package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.GuiaDespachoRepository;
import com.megacentro.service.GuiaDespachoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuiaDespachoServiceImpl extends AbstractCrudService<GuiaDespacho, Integer> implements GuiaDespachoService {

    private final GuiaDespachoRepository repository;

    public GuiaDespachoServiceImpl(GuiaDespachoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<GuiaDespacho, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "GuiaDespacho";
    }

    @Override
    protected void antesDeRegistrar(GuiaDespacho e) {
        e.setIdGuia(null);
    }

    @Override
    protected void asignarId(GuiaDespacho e, Integer id) {
        e.setIdGuia(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(GuiaDespacho e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(GuiaDespacho e) {
        e.setActivo(true);
    }

    @Override
    public List<GuiaDespacho> listarActivos() {
        return repository.findByActivoTrue();
    }

}
