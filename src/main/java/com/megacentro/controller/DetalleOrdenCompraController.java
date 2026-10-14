package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetalleOrdenCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_orden_compra (ms-compras). */
@RestController
@RequestMapping("/api/detalle-orden-compra")
@RequiredArgsConstructor
public class DetalleOrdenCompraController {

    private final DetalleOrdenCompraService service;

    @GetMapping
    public List<DetalleOrdenCompra> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetalleOrdenCompra> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idOrdenCompra}/{idProducto}")
    public DetalleOrdenCompra buscar(@PathVariable Integer idOrdenCompra,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetalleOrdenCompraId(idOrdenCompra, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetalleOrdenCompra> registrar(@Valid @RequestBody DetalleOrdenCompra body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idOrdenCompra}/{idProducto}")
    public DetalleOrdenCompra actualizar(@PathVariable Integer idOrdenCompra,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetalleOrdenCompra body) {
        return service.actualizar(new DetalleOrdenCompraId(idOrdenCompra, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idOrdenCompra}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idOrdenCompra,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetalleOrdenCompraId(idOrdenCompra, idProducto));
        return Map.of("mensaje", "DetalleOrdenCompra dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idOrdenCompra}/{idProducto}/habilitar")
    public DetalleOrdenCompra habilitar(@PathVariable Integer idOrdenCompra,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetalleOrdenCompraId(idOrdenCompra, idProducto));
    }

}
