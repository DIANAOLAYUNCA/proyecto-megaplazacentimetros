package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetalleOrdenCompraRepository;
import com.megacentro.service.DetalleOrdenCompraService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenCompraServiceImpl extends AbstractCrudService<DetalleOrdenCompra, DetalleOrdenCompraId> implements DetalleOrdenCompraService {

    private final DetalleOrdenCompraRepository repository;

    public DetalleOrdenCompraServiceImpl(DetalleOrdenCompraRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetalleOrdenCompra, DetalleOrdenCompraId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetalleOrdenCompra";
    }

    @Override
    protected void antesDeRegistrar(DetalleOrdenCompra e) {
        if (e.getOrdenCompra() == null || e.getOrdenCompra().getIdOrdenCompra() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar ordenCompra.idOrdenCompra y producto.idProducto");
        }
        e.setId(new DetalleOrdenCompraId(e.getOrdenCompra().getIdOrdenCompra(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetalleOrdenCompra con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetalleOrdenCompra e, DetalleOrdenCompraId id) {
        e.setId(id);
        OrdenCompra a = new OrdenCompra();
        a.setIdOrdenCompra(id.getIdOrdenCompra());
        e.setOrdenCompra(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetalleOrdenCompra e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetalleOrdenCompra e) {
        e.setActivo(true);
    }

    @Override
    public List<DetalleOrdenCompra> listarActivos() {
        return repository.findByActivoTrue();
    }

}
