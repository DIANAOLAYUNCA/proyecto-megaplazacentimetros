package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.IncidenciaDespachoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de incidencia_despacho (ms-despacho). */
@RestController
@RequestMapping("/api/incidencia-despacho")
@RequiredArgsConstructor
public class IncidenciaDespachoController {

    private final IncidenciaDespachoService service;

    @GetMapping
    public List<IncidenciaDespacho> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<IncidenciaDespacho> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public IncidenciaDespacho buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<IncidenciaDespacho> registrar(@Valid @RequestBody IncidenciaDespacho body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public IncidenciaDespacho actualizar(@PathVariable Integer id,
            @Valid @RequestBody IncidenciaDespacho body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "IncidenciaDespacho dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public IncidenciaDespacho habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
