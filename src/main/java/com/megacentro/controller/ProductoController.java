package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de producto (ms-inventario). */
@RestController
@RequestMapping("/api/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Producto> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Producto> registrar(@Valid @RequestBody Producto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Integer id,
            @Valid @RequestBody Producto body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Producto dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Producto habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
