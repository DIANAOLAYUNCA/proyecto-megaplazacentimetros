package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DespachoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de despacho (ms-despacho). */
@RestController
@RequestMapping("/api/despacho")
@RequiredArgsConstructor
public class DespachoController {

    private final DespachoService service;

    @GetMapping
    public List<Despacho> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Despacho> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Despacho buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Despacho> registrar(@Valid @RequestBody Despacho body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Despacho actualizar(@PathVariable Integer id,
            @Valid @RequestBody Despacho body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Despacho dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Despacho habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
