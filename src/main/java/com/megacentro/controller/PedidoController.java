package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de pedido (ms-ventas). */
@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @GetMapping
    public List<Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Pedido> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Pedido> registrar(@Valid @RequestBody Pedido body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Pedido actualizar(@PathVariable Integer id,
            @Valid @RequestBody Pedido body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Pedido dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Pedido habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
