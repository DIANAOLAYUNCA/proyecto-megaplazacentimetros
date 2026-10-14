package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.CategoriaRepository;
import com.megacentro.service.CategoriaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl extends AbstractCrudService<Categoria, Integer> implements CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaServiceImpl(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Categoria, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Categoria";
    }

    @Override
    protected void antesDeRegistrar(Categoria e) {
        e.setIdCategoria(null);
    }

    @Override
    protected void asignarId(Categoria e, Integer id) {
        e.setIdCategoria(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Categoria e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Categoria e) {
        e.setActivo(true);
    }

    @Override
    public List<Categoria> listarActivos() {
        return repository.findByActivoTrue();
    }

}
