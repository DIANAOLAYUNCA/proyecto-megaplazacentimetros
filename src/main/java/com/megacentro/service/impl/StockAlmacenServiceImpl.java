package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.StockAlmacenRepository;
import com.megacentro.service.StockAlmacenService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAlmacenServiceImpl extends AbstractCrudService<StockAlmacen, StockAlmacenId> implements StockAlmacenService {

    private final StockAlmacenRepository repository;

    public StockAlmacenServiceImpl(StockAlmacenRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<StockAlmacen, StockAlmacenId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "StockAlmacen";
    }

    @Override
    protected void antesDeRegistrar(StockAlmacen e) {
        if (e.getProducto() == null || e.getProducto().getIdProducto() == null
                || e.getAlmacen() == null || e.getAlmacen().getIdAlmacen() == null) {
            throw new IllegalArgumentException("Debe enviar producto.idProducto y almacen.idAlmacen");
        }
        e.setId(new StockAlmacenId(e.getProducto().getIdProducto(), e.getAlmacen().getIdAlmacen()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de StockAlmacen con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(StockAlmacen e, StockAlmacenId id) {
        e.setId(id);
        Producto a = new Producto();
        a.setIdProducto(id.getIdProducto());
        e.setProducto(a);
        Almacen b = new Almacen();
        b.setIdAlmacen(id.getIdAlmacen());
        e.setAlmacen(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(StockAlmacen e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(StockAlmacen e) {
        e.setActivo(true);
    }

    @Override
    public List<StockAlmacen> listarActivos() {
        return repository.findByActivoTrue();
    }

}
