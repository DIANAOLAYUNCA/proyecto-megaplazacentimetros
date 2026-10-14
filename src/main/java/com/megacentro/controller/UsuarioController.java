package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de usuario (ms-seguridad). */
@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<Usuario> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Integer id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id,
            @Valid @RequestBody Usuario body) {
        return service.actualizar(id, body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{id}")
    public Map<String, String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return Map.of("mensaje", "Usuario dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{id}/habilitar")
    public Usuario habilitar(@PathVariable Integer id) {
        return service.habilitar(id);
    }

}
