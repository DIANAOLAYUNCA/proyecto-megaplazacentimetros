package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PagoClienteRepository;
import com.megacentro.service.PagoClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoClienteServiceImpl extends AbstractCrudService<PagoCliente, Integer> implements PagoClienteService {

    private final PagoClienteRepository repository;

    public PagoClienteServiceImpl(PagoClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<PagoCliente, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "PagoCliente";
    }

    @Override
    protected void antesDeRegistrar(PagoCliente e) {
        e.setIdPagoCliente(null);
    }

    @Override
    protected void asignarId(PagoCliente e, Integer id) {
        e.setIdPagoCliente(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(PagoCliente e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(PagoCliente e) {
        e.setActivo(true);
    }

    @Override
    public List<PagoCliente> listarActivos() {
        return repository.findByActivoTrue();
    }

}
