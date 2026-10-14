package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.CampanaRepository;
import com.megacentro.service.CampanaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampanaServiceImpl extends AbstractCrudService<Campana, Integer> implements CampanaService {

    private final CampanaRepository repository;

    public CampanaServiceImpl(CampanaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Campana, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Campana";
    }

    @Override
    protected void antesDeRegistrar(Campana e) {
        e.setIdCampana(null);
    }

    @Override
    protected void asignarId(Campana e, Integer id) {
        e.setIdCampana(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Campana e) {
        e.setEstado("CANCELADA");
    }

    @Override
    protected void marcarActivo(Campana e) {
        e.setEstado("PLANIFICADA");
    }

    @Override
    public List<Campana> listarActivos() {
        return repository.findByEstadoNot("CANCELADA");
    }

}
