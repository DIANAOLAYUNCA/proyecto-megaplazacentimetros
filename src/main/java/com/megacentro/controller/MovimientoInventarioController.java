package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de movimiento_inventario (ms-inventario). */
@RestController
@RequestMapping("/api/movimiento-inventario")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService service;

    @GetMapping
    public List<MovimientoInventario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MovimientoInventario buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<MovimientoInventario> registrar(@Valid @RequestBody MovimientoInventario body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public MovimientoInventario actualizar(@PathVariable Long id,
            @Valid @RequestBody MovimientoInventario body) {
        return service.actualizar(id, body);
    }

}
