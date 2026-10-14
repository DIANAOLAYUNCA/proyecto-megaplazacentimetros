package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.RolPermisoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de rol_permiso (ms-seguridad). */
@RestController
@RequestMapping("/api/rol-permiso")
@RequiredArgsConstructor
public class RolPermisoController {

    private final RolPermisoService service;

    @GetMapping
    public List<RolPermiso> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<RolPermiso> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idRol}/{idPermiso}")
    public RolPermiso buscar(@PathVariable Integer idRol,
            @PathVariable Integer idPermiso) {
        return service.buscar(new RolPermisoId(idRol, idPermiso));
    }

    @PostMapping
    public ResponseEntity<RolPermiso> registrar(@Valid @RequestBody RolPermiso body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idRol}/{idPermiso}")
    public RolPermiso actualizar(@PathVariable Integer idRol,
            @PathVariable Integer idPermiso,
            @Valid @RequestBody RolPermiso body) {
        return service.actualizar(new RolPermisoId(idRol, idPermiso), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idRol}/{idPermiso}")
    public Map<String, String> eliminar(@PathVariable Integer idRol,
            @PathVariable Integer idPermiso) {
        service.eliminar(new RolPermisoId(idRol, idPermiso));
        return Map.of("mensaje", "RolPermiso dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idRol}/{idPermiso}/habilitar")
    public RolPermiso habilitar(@PathVariable Integer idRol,
            @PathVariable Integer idPermiso) {
        return service.habilitar(new RolPermisoId(idRol, idPermiso));
    }

}
