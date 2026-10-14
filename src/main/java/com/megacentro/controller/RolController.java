package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de rol (ms-seguridad). */
@RestController
@RequestMapping("/api/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolService service;

    @GetMapping
    public List<Rol> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Rol> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Rol buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Rol> registrar(@Valid @RequestBody Rol body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Rol actualizar(@PathVariable Integer id,
            @Valid @RequestBody Rol body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Rol dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Rol habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
