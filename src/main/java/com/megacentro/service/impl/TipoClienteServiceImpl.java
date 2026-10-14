package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.TipoClienteRepository;
import com.megacentro.service.TipoClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoClienteServiceImpl extends AbstractCrudService<TipoCliente, Integer> implements TipoClienteService {

    private final TipoClienteRepository repository;

    public TipoClienteServiceImpl(TipoClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<TipoCliente, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "TipoCliente";
    }

    @Override
    protected void antesDeRegistrar(TipoCliente e) {
        e.setIdTipoCliente(null);
    }

    @Override
    protected void asignarId(TipoCliente e, Integer id) {
        e.setIdTipoCliente(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(TipoCliente e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(TipoCliente e) {
        e.setActivo(true);
    }

    @Override
    public List<TipoCliente> listarActivos() {
        return repository.findByActivoTrue();
    }

}
