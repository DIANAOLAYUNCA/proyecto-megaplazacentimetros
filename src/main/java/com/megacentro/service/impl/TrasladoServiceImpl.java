package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.TrasladoRepository;
import com.megacentro.service.TrasladoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrasladoServiceImpl extends AbstractCrudService<Traslado, Integer> implements TrasladoService {

    private final TrasladoRepository repository;

    public TrasladoServiceImpl(TrasladoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Traslado, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Traslado";
    }

    @Override
    protected void antesDeRegistrar(Traslado e) {
        e.setIdTraslado(null);
    }

    @Override
    protected void asignarId(Traslado e, Integer id) {
        e.setIdTraslado(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Traslado e) {
        e.setEstado("ANULADO");
    }

    @Override
    protected void marcarActivo(Traslado e) {
        e.setEstado("PENDIENTE");
    }

    @Override
    public List<Traslado> listarActivos() {
        return repository.findByEstadoNot("ANULADO");
    }

}
