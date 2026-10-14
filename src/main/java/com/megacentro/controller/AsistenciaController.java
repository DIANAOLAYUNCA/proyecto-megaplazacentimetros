package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de asistencia (ms-personal). */
@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService service;

    @GetMapping
    public List<Asistencia> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Asistencia> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Asistencia buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Asistencia> registrar(@Valid @RequestBody Asistencia body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Asistencia actualizar(@PathVariable Integer id,
            @Valid @RequestBody Asistencia body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Asistencia dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Asistencia habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
