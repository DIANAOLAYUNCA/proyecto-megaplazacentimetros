package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.MovimientoInventarioRepository;
import com.megacentro.service.MovimientoInventarioService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoInventarioServiceImpl extends AbstractCrudService<MovimientoInventario, Long> implements MovimientoInventarioService {

    private final MovimientoInventarioRepository repository;

    public MovimientoInventarioServiceImpl(MovimientoInventarioRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<MovimientoInventario, Long> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "MovimientoInventario";
    }

    @Override
    protected void antesDeRegistrar(MovimientoInventario e) {
        e.setIdMovimiento(null);
    }

    @Override
    protected void asignarId(MovimientoInventario e, Long id) {
        e.setIdMovimiento(id);
    }

}
