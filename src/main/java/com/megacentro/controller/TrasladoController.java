package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.TrasladoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de traslado (ms-almacenes). */
@RestController
@RequestMapping("/api/traslado")
@RequiredArgsConstructor
public class TrasladoController {

    private final TrasladoService service;

    @GetMapping
    public List<Traslado> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Traslado> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Traslado buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Traslado> registrar(@Valid @RequestBody Traslado body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Traslado actualizar(@PathVariable Integer id,
            @Valid @RequestBody Traslado body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Traslado dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Traslado habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
