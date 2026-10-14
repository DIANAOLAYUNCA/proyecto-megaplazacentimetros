package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.ProgramaFidelizacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de programa_fidelizacion (ms-marketing). */
@RestController
@RequestMapping("/api/programa-fidelizacion")
@RequiredArgsConstructor
public class ProgramaFidelizacionController {

    private final ProgramaFidelizacionService service;

    @GetMapping
    public List<ProgramaFidelizacion> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<ProgramaFidelizacion> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public ProgramaFidelizacion buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<ProgramaFidelizacion> registrar(@Valid @RequestBody ProgramaFidelizacion body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public ProgramaFidelizacion actualizar(@PathVariable Integer id,
            @Valid @RequestBody ProgramaFidelizacion body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "ProgramaFidelizacion dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public ProgramaFidelizacion habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
