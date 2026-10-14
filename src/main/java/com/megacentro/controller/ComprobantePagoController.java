package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.ComprobantePagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de comprobante_pago (ms-pagos). */
@RestController
@RequestMapping("/api/comprobante-pago")
@RequiredArgsConstructor
public class ComprobantePagoController {

    private final ComprobantePagoService service;

    @GetMapping
    public List<ComprobantePago> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<ComprobantePago> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public ComprobantePago buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<ComprobantePago> registrar(@Valid @RequestBody ComprobantePago body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public ComprobantePago actualizar(@PathVariable Integer id,
            @Valid @RequestBody ComprobantePago body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "ComprobantePago dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public ComprobantePago habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
