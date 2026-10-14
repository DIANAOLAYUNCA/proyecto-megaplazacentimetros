package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de empleado (ms-personal). */
@RestController
@RequestMapping("/api/empleado")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    public List<Empleado> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Empleado> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Empleado buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Empleado> registrar(@Valid @RequestBody Empleado body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Integer id,
            @Valid @RequestBody Empleado body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Empleado dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Empleado habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
