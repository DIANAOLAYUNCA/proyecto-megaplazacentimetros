package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.MetodoPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de metodo_pago (ms-pagos). */
@RestController
@RequestMapping("/api/metodo-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService service;

    @GetMapping
    public List<MetodoPago> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<MetodoPago> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public MetodoPago buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<MetodoPago> registrar(@Valid @RequestBody MetodoPago body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public MetodoPago actualizar(@PathVariable Integer id,
            @Valid @RequestBody MetodoPago body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "MetodoPago dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public MetodoPago habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
