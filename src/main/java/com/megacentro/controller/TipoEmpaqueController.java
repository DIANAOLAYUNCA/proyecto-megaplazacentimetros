package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.TipoEmpaqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de tipo_empaque (ms-inventario). */
@RestController
@RequestMapping("/api/tipo-empaque")
@RequiredArgsConstructor
public class TipoEmpaqueController {

    private final TipoEmpaqueService service;

    @GetMapping
    public List<TipoEmpaque> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<TipoEmpaque> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public TipoEmpaque buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<TipoEmpaque> registrar(@Valid @RequestBody TipoEmpaque body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public TipoEmpaque actualizar(@PathVariable Integer id,
            @Valid @RequestBody TipoEmpaque body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "TipoEmpaque dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public TipoEmpaque habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
