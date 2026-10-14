package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PagoClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de pago_cliente (ms-pagos). */
@RestController
@RequestMapping("/api/pago-cliente")
@RequiredArgsConstructor
public class PagoClienteController {

    private final PagoClienteService service;

    @GetMapping
    public List<PagoCliente> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<PagoCliente> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public PagoCliente buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<PagoCliente> registrar(@Valid @RequestBody PagoCliente body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public PagoCliente actualizar(@PathVariable Integer id,
            @Valid @RequestBody PagoCliente body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "PagoCliente dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public PagoCliente habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
