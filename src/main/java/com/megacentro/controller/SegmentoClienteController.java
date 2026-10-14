package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.SegmentoClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de segmento_cliente (ms-marketing). */
@RestController
@RequestMapping("/api/segmento-cliente")
@RequiredArgsConstructor
public class SegmentoClienteController {

    private final SegmentoClienteService service;

    @GetMapping
    public List<SegmentoCliente> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<SegmentoCliente> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public SegmentoCliente buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<SegmentoCliente> registrar(@Valid @RequestBody SegmentoCliente body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public SegmentoCliente actualizar(@PathVariable Integer id,
            @Valid @RequestBody SegmentoCliente body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "SegmentoCliente dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public SegmentoCliente habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
