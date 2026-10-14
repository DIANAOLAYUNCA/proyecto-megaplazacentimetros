package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetalleFacturaVentaRepository;
import com.megacentro.service.DetalleFacturaVentaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleFacturaVentaServiceImpl extends AbstractCrudService<DetalleFacturaVenta, DetalleFacturaVentaId> implements DetalleFacturaVentaService {

    private final DetalleFacturaVentaRepository repository;

    public DetalleFacturaVentaServiceImpl(DetalleFacturaVentaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetalleFacturaVenta, DetalleFacturaVentaId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetalleFacturaVenta";
    }

    @Override
    protected void antesDeRegistrar(DetalleFacturaVenta e) {
        if (e.getFacturaVenta() == null || e.getFacturaVenta().getIdFacturaVenta() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar facturaVenta.idFacturaVenta y producto.idProducto");
        }
        e.setId(new DetalleFacturaVentaId(e.getFacturaVenta().getIdFacturaVenta(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetalleFacturaVenta con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetalleFacturaVenta e, DetalleFacturaVentaId id) {
        e.setId(id);
        FacturaVenta a = new FacturaVenta();
        a.setIdFacturaVenta(id.getIdFacturaVenta());
        e.setFacturaVenta(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetalleFacturaVenta e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetalleFacturaVenta e) {
        e.setActivo(true);
    }

    @Override
    public List<DetalleFacturaVenta> listarActivos() {
        return repository.findByActivoTrue();
    }

}
