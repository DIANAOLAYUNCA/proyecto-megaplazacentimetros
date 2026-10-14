package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PuntosClienteRepository;
import com.megacentro.service.PuntosClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntosClienteServiceImpl extends AbstractCrudService<PuntosCliente, Integer> implements PuntosClienteService {

    private final PuntosClienteRepository repository;

    public PuntosClienteServiceImpl(PuntosClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<PuntosCliente, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "PuntosCliente";
    }

    @Override
    protected void antesDeRegistrar(PuntosCliente e) {
        e.setIdMovimientoPuntos(null);
    }

    @Override
    protected void asignarId(PuntosCliente e, Integer id) {
        e.setIdMovimientoPuntos(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(PuntosCliente e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(PuntosCliente e) {
        e.setActivo(true);
    }

    @Override
    public List<PuntosCliente> listarActivos() {
        return repository.findByActivoTrue();
    }

}
