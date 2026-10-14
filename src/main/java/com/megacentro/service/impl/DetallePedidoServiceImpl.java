package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.DetallePedidoRepository;
import com.megacentro.service.DetallePedidoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetallePedidoServiceImpl extends AbstractCrudService<DetallePedido, DetallePedidoId> implements DetallePedidoService {

    private final DetallePedidoRepository repository;

    public DetallePedidoServiceImpl(DetallePedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<DetallePedido, DetallePedidoId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "DetallePedido";
    }

    @Override
    protected void antesDeRegistrar(DetallePedido e) {
        if (e.getPedido() == null || e.getPedido().getIdPedido() == null
                || e.getProducto() == null || e.getProducto().getIdProducto() == null) {
            throw new IllegalArgumentException("Debe enviar pedido.idPedido y producto.idProducto");
        }
        e.setId(new DetallePedidoId(e.getPedido().getIdPedido(), e.getProducto().getIdProducto()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de DetallePedido con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(DetallePedido e, DetallePedidoId id) {
        e.setId(id);
        Pedido a = new Pedido();
        a.setIdPedido(id.getIdPedido());
        e.setPedido(a);
        Producto b = new Producto();
        b.setIdProducto(id.getIdProducto());
        e.setProducto(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(DetallePedido e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(DetallePedido e) {
        e.setActivo(true);
    }

    @Override
    public List<DetallePedido> listarActivos() {
        return repository.findByActivoTrue();
    }

}
