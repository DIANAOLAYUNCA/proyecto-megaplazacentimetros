package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de cargo (ms-personal). */
@RestController
@RequestMapping("/api/cargo")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService service;

    @GetMapping
    public List<Cargo> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Cargo> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Cargo buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Cargo> registrar(@Valid @RequestBody Cargo body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Cargo actualizar(@PathVariable Integer id,
            @Valid @RequestBody Cargo body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Cargo dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Cargo habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
