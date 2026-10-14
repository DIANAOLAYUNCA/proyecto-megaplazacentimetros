package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.UsuarioRepository;
import com.megacentro.service.UsuarioService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends AbstractCrudService<Usuario, Integer> implements UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Usuario, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Usuario";
    }

    @Override
    protected void asignarId(Usuario e, Integer id) {
        e.setIdUsuario(id);
    }

    @Override
    protected void antesDeRegistrar(Usuario e) {
        e.setIdUsuario(null);
        if (e.getPasswordHash() == null || e.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("passwordHash es obligatorio");
        }
        e.setPasswordHash(com.megacentro.common.Hashing.sha256(e.getPasswordHash()));
    }

    @Override
    protected void antesDeActualizar(Usuario nuevo, Usuario existente) {
        if (nuevo.getPasswordHash() == null || nuevo.getPasswordHash().isBlank()) {
            nuevo.setPasswordHash(existente.getPasswordHash());
        } else {
            nuevo.setPasswordHash(com.megacentro.common.Hashing.sha256(nuevo.getPasswordHash()));
        }
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Usuario e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(Usuario e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Usuario> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
