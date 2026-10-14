package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.IncidenciaDespachoRepository;
import com.megacentro.service.IncidenciaDespachoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenciaDespachoServiceImpl extends AbstractCrudService<IncidenciaDespacho, Integer> implements IncidenciaDespachoService {

    private final IncidenciaDespachoRepository repository;

    public IncidenciaDespachoServiceImpl(IncidenciaDespachoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<IncidenciaDespacho, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "IncidenciaDespacho";
    }

    @Override
    protected void antesDeRegistrar(IncidenciaDespacho e) {
        e.setIdIncidencia(null);
    }

    @Override
    protected void asignarId(IncidenciaDespacho e, Integer id) {
        e.setIdIncidencia(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(IncidenciaDespacho e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(IncidenciaDespacho e) {
        e.setActivo(true);
    }

    @Override
    public List<IncidenciaDespacho> listarActivos() {
        return repository.findByActivoTrue();
    }

}
