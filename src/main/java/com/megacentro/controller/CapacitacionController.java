package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.CapacitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de capacitacion (ms-personal). */
@RestController
@RequestMapping("/api/capacitacion")
@RequiredArgsConstructor
public class CapacitacionController {

    private final CapacitacionService service;

    @GetMapping
    public List<Capacitacion> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Capacitacion> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Capacitacion buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Capacitacion> registrar(@Valid @RequestBody Capacitacion body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Capacitacion actualizar(@PathVariable Integer id,
            @Valid @RequestBody Capacitacion body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Capacitacion dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Capacitacion habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
