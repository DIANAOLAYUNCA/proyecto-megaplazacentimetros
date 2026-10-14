package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetalleFacturaVentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_factura_venta (ms-ventas). */
@RestController
@RequestMapping("/api/detalle-factura-venta")
@RequiredArgsConstructor
public class DetalleFacturaVentaController {

    private final DetalleFacturaVentaService service;

    @GetMapping
    public List<DetalleFacturaVenta> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetalleFacturaVenta> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idFacturaVenta}/{idProducto}")
    public DetalleFacturaVenta buscar(@PathVariable Integer idFacturaVenta,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetalleFacturaVentaId(idFacturaVenta, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetalleFacturaVenta> registrar(@Valid @RequestBody DetalleFacturaVenta body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idFacturaVenta}/{idProducto}")
    public DetalleFacturaVenta actualizar(@PathVariable Integer idFacturaVenta,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetalleFacturaVenta body) {
        return service.actualizar(new DetalleFacturaVentaId(idFacturaVenta, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idFacturaVenta}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idFacturaVenta,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetalleFacturaVentaId(idFacturaVenta, idProducto));
        return Map.of("mensaje", "DetalleFacturaVenta dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idFacturaVenta}/{idProducto}/habilitar")
    public DetalleFacturaVenta habilitar(@PathVariable Integer idFacturaVenta,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetalleFacturaVentaId(idFacturaVenta, idProducto));
    }

}
