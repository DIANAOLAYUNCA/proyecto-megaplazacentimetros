package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.AuditoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de auditoria (ms-seguridad). */
@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService service;

    @GetMapping
    public List<Auditoria> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Auditoria buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Auditoria> registrar(@Valid @RequestBody Auditoria body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Auditoria actualizar(@PathVariable Long id,
            @Valid @RequestBody Auditoria body) {
        return service.actualizar(id, body);
    }

}
