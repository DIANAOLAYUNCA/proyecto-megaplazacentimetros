package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetalleDespachoRepository;
import com.megacentro.service.DetalleDespachoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleDespachoServiceImpl extends AbstractCrudService<DetalleDespacho, DetalleDespachoId> implements DetalleDespachoService {

    private final DetalleDespachoRepository repository;

    public DetalleDespachoServiceImpl(DetalleDespachoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetalleDespacho, DetalleDespachoId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetalleDespacho";
    }

    @Override
    protected void antesDeRegistrar(DetalleDespacho e) {
        if (e.getDespacho() == null || e.getDespacho().getIdDespacho() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar despacho.idDespacho y producto.idProducto");
        }
        e.setId(new DetalleDespachoId(e.getDespacho().getIdDespacho(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetalleDespacho con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetalleDespacho e, DetalleDespachoId id) {
        e.setId(id);
        Despacho a = new Despacho();
        a.setIdDespacho(id.getIdDespacho());
        e.setDespacho(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetalleDespacho e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetalleDespacho e) {
        e.setActivo(true);
    }

    @Override
    public List<DetalleDespacho> listarActivos() {
        return repository.findByActivoTrue();
    }

}
