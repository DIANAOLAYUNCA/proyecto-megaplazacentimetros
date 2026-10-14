package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.FacturaProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de factura_proveedor (ms-compras). */
@RestController
@RequestMapping("/api/factura-proveedor")
@RequiredArgsConstructor
public class FacturaProveedorController {

    private final FacturaProveedorService service;

    @GetMapping
    public List<FacturaProveedor> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<FacturaProveedor> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public FacturaProveedor buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<FacturaProveedor> registrar(@Valid @RequestBody FacturaProveedor body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public FacturaProveedor actualizar(@PathVariable Integer id,
            @Valid @RequestBody FacturaProveedor body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "FacturaProveedor dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public FacturaProveedor habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
