package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.TransporteRepository;
import com.megacentro.service.TransporteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporteServiceImpl extends AbstractCrudService<Transporte, Integer> implements TransporteService {

    private final TransporteRepository repository;

    public TransporteServiceImpl(TransporteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Transporte, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Transporte";
    }

    @Override
    protected void antesDeRegistrar(Transporte e) {
        e.setIdTransporte(null);
    }

    @Override
    protected void asignarId(Transporte e, Integer id) {
        e.setIdTransporte(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Transporte e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Transporte e) {
        e.setActivo(true);
    }

    @Override
    public List<Transporte> listarActivos() {
        return repository.findByActivoTrue();
    }

}
