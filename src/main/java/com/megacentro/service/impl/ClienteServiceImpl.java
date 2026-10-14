package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.ClienteRepository;
import com.megacentro.service.ClienteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl extends AbstractCrudService<Cliente, Integer> implements ClienteService {

    private final ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Cliente, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Cliente";
    }

    @Override
    protected void antesDeRegistrar(Cliente e) {
        e.setIdCliente(null);
    }

    @Override
    protected void asignarId(Cliente e, Integer id) {
        e.setIdCliente(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Cliente e) {
        e.setEstado("INACTIVO");
    }

    @Override
    protected void marcarActivo(Cliente e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Cliente> listarActivos() {
        return repository.findByEstadoNot("INACTIVO");
    }

}
