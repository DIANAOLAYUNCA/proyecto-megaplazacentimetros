package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.TipoEmpaqueRepository;
import com.megacentro.service.TipoEmpaqueService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEmpaqueServiceImpl extends AbstractCrudService<TipoEmpaque, Integer> implements TipoEmpaqueService {

    private final TipoEmpaqueRepository repository;

    public TipoEmpaqueServiceImpl(TipoEmpaqueRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<TipoEmpaque, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "TipoEmpaque";
    }

    @Override
    protected void antesDeRegistrar(TipoEmpaque e) {
        e.setIdTipoEmpaque(null);
    }

    @Override
    protected void asignarId(TipoEmpaque e, Integer id) {
        e.setIdTipoEmpaque(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(TipoEmpaque e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(TipoEmpaque e) {
        e.setActivo(true);
    }

    @Override
    public List<TipoEmpaque> listarActivos() {
        return repository.findByActivoTrue();
    }

}
