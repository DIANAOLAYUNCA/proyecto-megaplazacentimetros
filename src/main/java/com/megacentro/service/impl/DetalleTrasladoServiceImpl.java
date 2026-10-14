package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetalleTrasladoRepository;
import com.megacentro.service.DetalleTrasladoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleTrasladoServiceImpl extends AbstractCrudService<DetalleTraslado, DetalleTrasladoId> implements DetalleTrasladoService {

    private final DetalleTrasladoRepository repository;

    public DetalleTrasladoServiceImpl(DetalleTrasladoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetalleTraslado, DetalleTrasladoId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetalleTraslado";
    }

    @Override
    protected void antesDeRegistrar(DetalleTraslado e) {
        if (e.getTraslado() == null || e.getTraslado().getIdTraslado() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar traslado.idTraslado y producto.idProducto");
        }
        e.setId(new DetalleTrasladoId(e.getTraslado().getIdTraslado(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetalleTraslado con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetalleTraslado e, DetalleTrasladoId id) {
        e.setId(id);
        Traslado a = new Traslado();
        a.setIdTraslado(id.getIdTraslado());
        e.setTraslado(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetalleTraslado e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetalleTraslado e) {
        e.setActivo(true);
    }

    @Override
    public List<DetalleTraslado> listarActivos() {
        return repository.findByActivoTrue();
    }

}
