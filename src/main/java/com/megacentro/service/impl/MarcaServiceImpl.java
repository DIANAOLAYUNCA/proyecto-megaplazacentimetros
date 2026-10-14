package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.MarcaRepository;
import com.megacentro.service.MarcaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl extends AbstractCrudService<Marca, Integer> implements MarcaService {

    private final MarcaRepository repository;

    public MarcaServiceImpl(MarcaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Marca, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Marca";
    }

    @Override
    protected void antesDeRegistrar(Marca e) {
        e.setIdMarca(null);
    }

    @Override
    protected void asignarId(Marca e, Integer id) {
        e.setIdMarca(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Marca e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Marca e) {
        e.setActivo(true);
    }

    @Override
    public List<Marca> listarActivos() {
        return repository.findByActivoTrue();
    }

}
