package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PedidoRepository;
import com.megacentro.service.PedidoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl extends AbstractCrudService<Pedido, Integer> implements PedidoService {

    private final PedidoRepository repository;

    public PedidoServiceImpl(PedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Pedido, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Pedido";
    }

    @Override
    protected void antesDeRegistrar(Pedido e) {
        e.setIdPedido(null);
    }

    @Override
    protected void asignarId(Pedido e, Integer id) {
        e.setIdPedido(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Pedido e) {
        e.setEstado("ANULADO");
    }

    @Override
    protected void marcarActivo(Pedido e) {
        e.setEstado("REGISTRADO");
    }

    @Override
    public List<Pedido> listarActivos() {
        return repository.findByEstadoNot("ANULADO");
    }

}
