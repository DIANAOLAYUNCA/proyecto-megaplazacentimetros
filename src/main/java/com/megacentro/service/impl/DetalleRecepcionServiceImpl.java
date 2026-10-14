package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetalleRecepcionRepository;
import com.megacentro.service.DetalleRecepcionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleRecepcionServiceImpl extends AbstractCrudService<DetalleRecepcion, DetalleRecepcionId> implements DetalleRecepcionService {

    private final DetalleRecepcionRepository repository;

    public DetalleRecepcionServiceImpl(DetalleRecepcionRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetalleRecepcion, DetalleRecepcionId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetalleRecepcion";
    }

    @Override
    protected void antesDeRegistrar(DetalleRecepcion e) {
        if (e.getRecepcion() == null || e.getRecepcion().getIdRecepcion() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar recepcion.idRecepcion y producto.idProducto");
        }
        e.setId(new DetalleRecepcionId(e.getRecepcion().getIdRecepcion(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetalleRecepcion con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetalleRecepcion e, DetalleRecepcionId id) {
        e.setId(id);
        RecepcionCompra a = new RecepcionCompra();
        a.setIdRecepcion(id.getIdRecepcion());
        e.setRecepcion(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetalleRecepcion e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetalleRecepcion e) {
        e.setActivo(true);
    }

    @Override
    public List<DetalleRecepcion> listarActivos() {
        return repository.findByActivoTrue();
    }

}
