package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.FacturaVentaRepository;
import com.megacentro.service.FacturaVentaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaVentaServiceImpl extends AbstractCrudService<FacturaVenta, Integer> implements FacturaVentaService {

    private final FacturaVentaRepository repository;

    public FacturaVentaServiceImpl(FacturaVentaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<FacturaVenta, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "FacturaVenta";
    }

    @Override
    protected void antesDeRegistrar(FacturaVenta e) {
        e.setIdFacturaVenta(null);
    }

    @Override
    protected void asignarId(FacturaVenta e, Integer id) {
        e.setIdFacturaVenta(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(FacturaVenta e) {
        e.setEstado("ANULADA");
    }

    @Override
    protected void marcarActivo(FacturaVenta e) {
        e.setEstado("EMITIDA");
    }

    @Override
    public List<FacturaVenta> listarActivos() {
        return repository.findByEstadoNot("ANULADA");
    }

}
