package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.CanjeRecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de canje_recompensa (ms-marketing). */
@RestController
@RequestMapping("/api/canje-recompensa")
@RequiredArgsConstructor
public class CanjeRecompensaController {

    private final CanjeRecompensaService service;

    @GetMapping
    public List<CanjeRecompensa> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<CanjeRecompensa> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public CanjeRecompensa buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<CanjeRecompensa> registrar(@Valid @RequestBody CanjeRecompensa body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public CanjeRecompensa actualizar(@PathVariable Integer id,
            @Valid @RequestBody CanjeRecompensa body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "CanjeRecompensa dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public CanjeRecompensa habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
