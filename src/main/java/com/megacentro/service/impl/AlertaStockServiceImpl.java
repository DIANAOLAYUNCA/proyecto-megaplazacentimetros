package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.AlertaStockRepository;
import com.megacentro.service.AlertaStockService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaStockServiceImpl extends AbstractCrudService<AlertaStock, Integer> implements AlertaStockService {

    private final AlertaStockRepository repository;

    public AlertaStockServiceImpl(AlertaStockRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<AlertaStock, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "AlertaStock";
    }

    @Override
    protected void antesDeRegistrar(AlertaStock e) {
        e.setIdAlerta(null);
    }

    @Override
    protected void asignarId(AlertaStock e, Integer id) {
        e.setIdAlerta(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(AlertaStock e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(AlertaStock e) {
        e.setActivo(true);
    }

    @Override
    public List<AlertaStock> listarActivos() {
        return repository.findByActivoTrue();
    }

}
