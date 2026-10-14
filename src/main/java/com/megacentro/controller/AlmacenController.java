package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.AlmacenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de almacen (ms-almacenes). */
@RestController
@RequestMapping("/api/almacen")
@RequiredArgsConstructor
public class AlmacenController {

    private final AlmacenService service;

    @GetMapping
    public List<Almacen> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Almacen> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Almacen buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Almacen> registrar(@Valid @RequestBody Almacen body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Almacen actualizar(@PathVariable Integer id,
            @Valid @RequestBody Almacen body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Almacen dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Almacen habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
