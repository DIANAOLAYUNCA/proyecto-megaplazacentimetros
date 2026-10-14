package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.CargoRepository;
import com.megacentro.service.CargoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServiceImpl extends AbstractCrudService<Cargo, Integer> implements CargoService {

    private final CargoRepository repository;

    public CargoServiceImpl(CargoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Cargo, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Cargo";
    }

    @Override
    protected void antesDeRegistrar(Cargo e) {
        e.setIdCargo(null);
    }

    @Override
    protected void asignarId(Cargo e, Integer id) {
        e.setIdCargo(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Cargo e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Cargo e) {
        e.setActivo(true);
    }

    @Override
    public List<Cargo> listarActivos() {
        return repository.findByActivoTrue();
    }

}
