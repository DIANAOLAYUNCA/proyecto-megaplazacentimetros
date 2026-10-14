package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.AsistenciaRepository;
import com.megacentro.service.AsistenciaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaServiceImpl extends AbstractCrudService<Asistencia, Integer> implements AsistenciaService {

    private final AsistenciaRepository repository;

    public AsistenciaServiceImpl(AsistenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Asistencia, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Asistencia";
    }

    @Override
    protected void antesDeRegistrar(Asistencia e) {
        e.setIdAsistencia(null);
    }

    @Override
    protected void asignarId(Asistencia e, Integer id) {
        e.setIdAsistencia(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Asistencia e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(Asistencia e) {
        e.setActivo(true);
    }

    @Override
    public List<Asistencia> listarActivos() {
        return repository.findByActivoTrue();
    }

}
