package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.RecompensaRepository;
import com.megacentro.service.RecompensaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecompensaServiceImpl extends AbstractCrudService<Recompensa, Integer> implements RecompensaService {

    private final RecompensaRepository repository;

    public RecompensaServiceImpl(RecompensaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Recompensa, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Recompensa";
    }

    @Override
    protected void antesDeRegistrar(Recompensa e) {
        e.setIdRecompensa(null);
    }

    @Override
    protected void asignarId(Recompensa e, Integer id) {
        e.setIdRecompensa(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Recompensa e) {
        e.setEstado("INACTIVA");
    }

    @Override
    protected void marcarActivo(Recompensa e) {
        e.setEstado("DISPONIBLE");
    }

    @Override
    public List<Recompensa> listarActivos() {
        return repository.findByEstadoNot("INACTIVA");
    }

}
