package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.AlertaStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de alerta_stock (ms-inventario). */
@RestController
@RequestMapping("/api/alerta-stock")
@RequiredArgsConstructor
public class AlertaStockController {

    private final AlertaStockService service;

    @GetMapping
    public List<AlertaStock> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<AlertaStock> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public AlertaStock buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<AlertaStock> registrar(@Valid @RequestBody AlertaStock body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public AlertaStock actualizar(@PathVariable Integer id,
            @Valid @RequestBody AlertaStock body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "AlertaStock dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public AlertaStock habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
