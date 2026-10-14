package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.RecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de recompensa (ms-marketing). */
@RestController
@RequestMapping("/api/recompensa")
@RequiredArgsConstructor
public class RecompensaController {

    private final RecompensaService service;

    @GetMapping
    public List<Recompensa> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Recompensa> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Recompensa buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Recompensa> registrar(@Valid @RequestBody Recompensa body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Recompensa actualizar(@PathVariable Integer id,
            @Valid @RequestBody Recompensa body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Recompensa dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Recompensa habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
