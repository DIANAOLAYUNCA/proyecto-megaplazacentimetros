package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de cliente (ms-ventas). */
@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Cliente> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> registrar(@Valid @RequestBody Cliente body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Integer id,
            @Valid @RequestBody Cliente body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Cliente dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Cliente habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
