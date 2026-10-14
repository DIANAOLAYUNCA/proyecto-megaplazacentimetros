package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.AuditoriaRepository;
import com.megacentro.service.AuditoriaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaServiceImpl extends AbstractCrudService<Auditoria, Long> implements AuditoriaService {

    private final AuditoriaRepository repository;

    public AuditoriaServiceImpl(AuditoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Auditoria, Long> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Auditoria";
    }

    @Override
    protected void antesDeRegistrar(Auditoria e) {
        e.setIdAuditoria(null);
    }

    @Override
    protected void asignarId(Auditoria e, Long id) {
        e.setIdAuditoria(id);
    }

}
