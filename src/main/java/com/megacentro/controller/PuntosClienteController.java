package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PuntosClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de puntos_cliente (ms-marketing). */
@RestController
@RequestMapping("/api/puntos-cliente")
@RequiredArgsConstructor
public class PuntosClienteController {

    private final PuntosClienteService service;

    @GetMapping
    public List<PuntosCliente> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<PuntosCliente> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public PuntosCliente buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<PuntosCliente> registrar(@Valid @RequestBody PuntosCliente body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public PuntosCliente actualizar(@PathVariable Integer id,
            @Valid @RequestBody PuntosCliente body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "PuntosCliente dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public PuntosCliente habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
