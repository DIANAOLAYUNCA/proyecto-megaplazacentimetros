package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de sucursal (ms-almacenes). */
@RestController
@RequestMapping("/api/sucursal")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService service;

    @GetMapping
    public List<Sucursal> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Sucursal> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Sucursal buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Sucursal> registrar(@Valid @RequestBody Sucursal body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Sucursal actualizar(@PathVariable Integer id,
            @Valid @RequestBody Sucursal body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Sucursal dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Sucursal habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
