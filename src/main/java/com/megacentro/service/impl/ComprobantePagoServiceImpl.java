package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.ComprobantePagoRepository;
import com.megacentro.service.ComprobantePagoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComprobantePagoServiceImpl extends AbstractCrudService<ComprobantePago, Integer> implements ComprobantePagoService {

    private final ComprobantePagoRepository repository;

    public ComprobantePagoServiceImpl(ComprobantePagoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ComprobantePago, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "ComprobantePago";
    }

    @Override
    protected void antesDeRegistrar(ComprobantePago e) {
        e.setIdComprobante(null);
    }

    @Override
    protected void asignarId(ComprobantePago e, Integer id) {
        e.setIdComprobante(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(ComprobantePago e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(ComprobantePago e) {
        e.setActivo(true);
    }

    @Override
    public List<ComprobantePago> listarActivos() {
        return repository.findByActivoTrue();
    }

}
