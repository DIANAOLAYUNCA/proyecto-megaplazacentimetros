package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.TransporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de transporte (ms-despacho). */
@RestController
@RequestMapping("/api/transporte")
@RequiredArgsConstructor
public class TransporteController {

    private final TransporteService service;

    @GetMapping
    public List<Transporte> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Transporte> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Transporte buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Transporte> registrar(@Valid @RequestBody Transporte body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Transporte actualizar(@PathVariable Integer id,
            @Valid @RequestBody Transporte body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Transporte dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Transporte habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
