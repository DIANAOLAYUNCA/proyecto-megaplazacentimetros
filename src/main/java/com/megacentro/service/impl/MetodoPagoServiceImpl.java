package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.MetodoPagoRepository;
import com.megacentro.service.MetodoPagoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetodoPagoServiceImpl extends AbstractCrudService<MetodoPago, Integer> implements MetodoPagoService {

    private final MetodoPagoRepository repository;

    public MetodoPagoServiceImpl(MetodoPagoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<MetodoPago, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "MetodoPago";
    }

    @Override
    protected void antesDeRegistrar(MetodoPago e) {
        e.setIdMetodoPago(null);
    }

    @Override
    protected void asignarId(MetodoPago e, Integer id) {
        e.setIdMetodoPago(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(MetodoPago e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(MetodoPago e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<MetodoPago> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
