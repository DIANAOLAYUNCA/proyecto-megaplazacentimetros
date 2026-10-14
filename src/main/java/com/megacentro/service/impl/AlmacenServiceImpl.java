package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.AlmacenRepository;
import com.megacentro.service.AlmacenService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenServiceImpl extends AbstractCrudService<Almacen, Integer> implements AlmacenService {

    private final AlmacenRepository repository;

    public AlmacenServiceImpl(AlmacenRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Almacen, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Almacen";
    }

    @Override
    protected void antesDeRegistrar(Almacen e) {
        e.setIdAlmacen(null);
    }

    @Override
    protected void asignarId(Almacen e, Integer id) {
        e.setIdAlmacen(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Almacen e) {
        e.setEstado("CERRADO");
    }

    @Override
    protected void marcarActivo(Almacen e) {
        e.setEstado("OPERATIVO");
    }

    @Override
    public List<Almacen> listarActivos() {
        return repository.findByEstadoNot("CERRADO");
    }

}
