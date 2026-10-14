package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.RecepcionCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de recepcion_compra (ms-compras). */
@RestController
@RequestMapping("/api/recepcion-compra")
@RequiredArgsConstructor
public class RecepcionCompraController {

    private final RecepcionCompraService service;

    @GetMapping
    public List<RecepcionCompra> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<RecepcionCompra> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public RecepcionCompra buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<RecepcionCompra> registrar(@Valid @RequestBody RecepcionCompra body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public RecepcionCompra actualizar(@PathVariable Integer id,
            @Valid @RequestBody RecepcionCompra body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "RecepcionCompra dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public RecepcionCompra habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
