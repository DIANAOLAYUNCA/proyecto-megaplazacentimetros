package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.PromocionProductoRepository;
import com.megacentro.service.PromocionProductoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionProductoServiceImpl extends AbstractCrudService<PromocionProducto, PromocionProductoId> implements PromocionProductoService {

    private final PromocionProductoRepository repository;

    public PromocionProductoServiceImpl(PromocionProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<PromocionProducto, PromocionProductoId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "PromocionProducto";
    }

    @Override
    protected void antesDeRegistrar(PromocionProducto e) {
        if (e.getPromocion() == null || e.getPromocion().getIdPromocion() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar promocion.idPromocion y producto.idProducto");
        }
        e.setId(new PromocionProductoId(e.getPromocion().getIdPromocion(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de PromocionProducto con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(PromocionProducto e, PromocionProductoId id) {
        e.setId(id);
        Promocion a = new Promocion();
        a.setIdPromocion(id.getIdPromocion());
        e.setPromocion(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(PromocionProducto e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(PromocionProducto e) {
        e.setActivo(true);
    }

    @Override
    public List<PromocionProducto> listarActivos() {
        return repository.findByActivoTrue();
    }

}
