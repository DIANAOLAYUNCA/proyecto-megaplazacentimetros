package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.TipoClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de tipo_cliente (ms-ventas). */
@RestController
@RequestMapping("/api/tipo-cliente")
@RequiredArgsConstructor
public class TipoClienteController {

    private final TipoClienteService service;

    @GetMapping
    public List<TipoCliente> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<TipoCliente> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public TipoCliente buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<TipoCliente> registrar(@Valid @RequestBody TipoCliente body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public TipoCliente actualizar(@PathVariable Integer id,
            @Valid @RequestBody TipoCliente body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "TipoCliente dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public TipoCliente habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
