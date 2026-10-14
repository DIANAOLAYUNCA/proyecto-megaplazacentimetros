package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.ProductoRepository;
import com.megacentro.service.ProductoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl extends AbstractCrudService<Producto, Integer> implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Producto, Integer> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "Producto";
    }

    @Override
    protected void antesDeRegistrar(Producto e) {
        e.setIdProducto(null);
    }

    @Override
    protected void asignarId(Producto e, Integer id) {
        e.setIdProducto(id);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(Producto e) {
        e.setEstado("DESCONTINUADO");
    }

    @Override
    protected void marcarActivo(Producto e) {
        e.setEstado("ACTIVO");
    }

    @Override
    public List<Producto> listarActivos() {
        return repository.findByEstadoNot("DESCONTINUADO");
    }

}
