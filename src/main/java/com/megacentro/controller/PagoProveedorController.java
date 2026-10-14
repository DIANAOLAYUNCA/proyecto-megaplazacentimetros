package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PagoProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de pago_proveedor (ms-pagos). */
@RestController
@RequestMapping("/api/pago-proveedor")
@RequiredArgsConstructor
public class PagoProveedorController {

    private final PagoProveedorService service;

    @GetMapping
    public List<PagoProveedor> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<PagoProveedor> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public PagoProveedor buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<PagoProveedor> registrar(@Valid @RequestBody PagoProveedor body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public PagoProveedor actualizar(@PathVariable Integer id,
            @Valid @RequestBody PagoProveedor body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "PagoProveedor dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public PagoProveedor habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
