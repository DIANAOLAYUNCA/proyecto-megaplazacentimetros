package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.ProgramaFidelizacionRepository;
import com.megacentro.service.ProgramaFidelizacionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramaFidelizacionServiceImpl extends AbstractCrudService<ProgramaFidelizacion, Integer> implements ProgramaFidelizacionService {

    private final ProgramaFidelizacionRepository repository;

    public ProgramaFidelizacionServiceImpl(ProgramaFidelizacionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<ProgramaFidelizacion, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "ProgramaFidelizacion";
    }

    @Override
    protected void antesDeRegistrar(ProgramaFidelizacion e) {
        e.setIdPrograma(null);
    }

    @Override
    protected void asignarId(ProgramaFidelizacion e, Integer id) {
        e.setIdPrograma(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(ProgramaFidelizacion e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(ProgramaFidelizacion e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<ProgramaFidelizacion> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
