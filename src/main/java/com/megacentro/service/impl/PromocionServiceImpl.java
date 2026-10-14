package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PromocionRepository;
import com.megacentro.service.PromocionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionServiceImpl extends AbstractCrudService<Promocion, Integer> implements PromocionService {

    private final PromocionRepository repository;

    public PromocionServiceImpl(PromocionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Promocion, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Promocion";
    }

    @Override
    protected void antesDeRegistrar(Promocion e) {
        e.setIdPromocion(null);
    }

    @Override
    protected void asignarId(Promocion e, Integer id) {
        e.setIdPromocion(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Promocion e) {
        e.setEstado("INACTIVA");
    }

    @Override
    protected void marcarActivo(Promocion e) {
        e.setEstado("ACTIVA");
    }

    @Override
    public List<Promocion> listarActivos() {
        return repository.findByEstadoNot("INACTIVA");
    }

}
