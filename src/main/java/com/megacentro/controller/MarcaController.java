package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de marca (ms-inventario). */
@RestController
@RequestMapping("/api/marca")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService service;

    @GetMapping
    public List<Marca> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Marca> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Marca buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Marca> registrar(@Valid @RequestBody Marca body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Marca actualizar(@PathVariable Integer id,
            @Valid @RequestBody Marca body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Marca dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Marca habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
