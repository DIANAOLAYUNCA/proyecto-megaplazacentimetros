package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de categoria (ms-inventario). */
@RestController
@RequestMapping("/api/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Categoria> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Categoria buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Categoria> registrar(@Valid @RequestBody Categoria body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Integer id,
            @Valid @RequestBody Categoria body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Categoria dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Categoria habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
