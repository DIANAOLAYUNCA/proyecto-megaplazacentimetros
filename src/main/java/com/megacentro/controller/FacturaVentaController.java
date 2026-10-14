package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.FacturaVentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de factura_venta (ms-ventas). */
@RestController
@RequestMapping("/api/factura-venta")
@RequiredArgsConstructor
public class FacturaVentaController {

    private final FacturaVentaService service;

    @GetMapping
    public List<FacturaVenta> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<FacturaVenta> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public FacturaVenta buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<FacturaVenta> registrar(@Valid @RequestBody FacturaVenta body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public FacturaVenta actualizar(@PathVariable Integer id,
            @Valid @RequestBody FacturaVenta body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "FacturaVenta dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public FacturaVenta habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
