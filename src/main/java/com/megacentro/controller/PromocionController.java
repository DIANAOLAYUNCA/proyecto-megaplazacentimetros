package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PromocionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de promocion (ms-marketing). */
@RestController
@RequestMapping("/api/promocion")
@RequiredArgsConstructor
public class PromocionController {

    private final PromocionService service;

    @GetMapping
    public List<Promocion> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Promocion> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Promocion buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Promocion> registrar(@Valid @RequestBody Promocion body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Promocion actualizar(@PathVariable Integer id,
            @Valid @RequestBody Promocion body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Promocion dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Promocion habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
