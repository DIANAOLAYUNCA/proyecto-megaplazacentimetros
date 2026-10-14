package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.SegmentoClienteRepository;
import com.megacentro.service.SegmentoClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentoClienteServiceImpl extends AbstractCrudService<SegmentoCliente, Integer> implements SegmentoClienteService {

    private final SegmentoClienteRepository repository;

    public SegmentoClienteServiceImpl(SegmentoClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<SegmentoCliente, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "SegmentoCliente";
    }

    @Override
    protected void antesDeRegistrar(SegmentoCliente e) {
        e.setIdSegmento(null);
    }

    @Override
    protected void asignarId(SegmentoCliente e, Integer id) {
        e.setIdSegmento(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(SegmentoCliente e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(SegmentoCliente e) {
        e.setActivo(true);
    }

    @Override
    public List<SegmentoCliente> listarActivos() {
        return repository.findByActivoTrue();
    }

}
