package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PermisoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de permiso (ms-seguridad). */
@RestController
@RequestMapping("/api/permiso")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService service;

    @GetMapping
    public List<Permiso> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Permiso> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Permiso buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Permiso> registrar(@Valid @RequestBody Permiso body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Permiso actualizar(@PathVariable Integer id,
            @Valid @RequestBody Permiso body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Permiso dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Permiso habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
