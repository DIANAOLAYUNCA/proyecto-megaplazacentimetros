package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.CanjeRecompensaRepository;
import com.megacentro.service.CanjeRecompensaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanjeRecompensaServiceImpl extends AbstractCrudService<CanjeRecompensa, Integer> implements CanjeRecompensaService {

    private final CanjeRecompensaRepository repository;

    public CanjeRecompensaServiceImpl(CanjeRecompensaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<CanjeRecompensa, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "CanjeRecompensa";
    }

    @Override
    protected void antesDeRegistrar(CanjeRecompensa e) {
        e.setIdCanje(null);
    }

    @Override
    protected void asignarId(CanjeRecompensa e, Integer id) {
        e.setIdCanje(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(CanjeRecompensa e) {
        e.setEstado("ANULADO");
    }

    @Override
    protected void marcarActivo(CanjeRecompensa e) {
        e.setEstado("SOLICITADO");
    }

    @Override
    public List<CanjeRecompensa> listarActivos() {
        return repository.findByEstadoNot("ANULADO");
    }

}
