package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.CampanaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de campana (ms-marketing). */
@RestController
@RequestMapping("/api/campana")
@RequiredArgsConstructor
public class CampanaController {

    private final CampanaService service;

    @GetMapping
    public List<Campana> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Campana> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Campana buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Campana> registrar(@Valid @RequestBody Campana body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Campana actualizar(@PathVariable Integer id,
            @Valid @RequestBody Campana body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Campana dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Campana habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
