package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.GuiaDespachoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de guia_despacho (ms-despacho). */
@RestController
@RequestMapping("/api/guia-despacho")
@RequiredArgsConstructor
public class GuiaDespachoController {

    private final GuiaDespachoService service;

    @GetMapping
    public List<GuiaDespacho> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<GuiaDespacho> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public GuiaDespacho buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<GuiaDespacho> registrar(@Valid @RequestBody GuiaDespacho body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public GuiaDespacho actualizar(@PathVariable Integer id,
            @Valid @RequestBody GuiaDespacho body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "GuiaDespacho dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public GuiaDespacho habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
