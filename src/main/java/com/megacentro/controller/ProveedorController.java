package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de proveedor (ms-compras). */
@RestController
@RequestMapping("/api/proveedor")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService service;

    @GetMapping
    public List<Proveedor> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Proveedor> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Proveedor buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Proveedor> registrar(@Valid @RequestBody Proveedor body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable Integer id,
            @Valid @RequestBody Proveedor body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Proveedor dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Proveedor habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
