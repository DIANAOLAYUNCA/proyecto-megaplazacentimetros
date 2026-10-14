package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.EmpleadoCapacitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de empleado_capacitacion (ms-personal). */
@RestController
@RequestMapping("/api/empleado-capacitacion")
@RequiredArgsConstructor
public class EmpleadoCapacitacionController {

    private final EmpleadoCapacitacionService service;

    @GetMapping
    public List<EmpleadoCapacitacion> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<EmpleadoCapacitacion> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idEmpleado}/{idCapacitacion}")
    public EmpleadoCapacitacion buscar(@PathVariable Integer idEmpleado,
            @PathVariable Integer idCapacitacion) {
        return service.buscar(new EmpleadoCapacitacionId(idEmpleado, idCapacitacion));
    }

    @PostMapping
    public ResponseEntity<EmpleadoCapacitacion> registrar(@Valid @RequestBody EmpleadoCapacitacion body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idEmpleado}/{idCapacitacion}")
    public EmpleadoCapacitacion actualizar(@PathVariable Integer idEmpleado,
            @PathVariable Integer idCapacitacion,
            @Valid @RequestBody EmpleadoCapacitacion body) {
        return service.actualizar(new EmpleadoCapacitacionId(idEmpleado, idCapacitacion), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idEmpleado}/{idCapacitacion}")
    public Map<String, String> eliminar(@PathVariable Integer idEmpleado,
            @PathVariable Integer idCapacitacion) {
        service.eliminar(new EmpleadoCapacitacionId(idEmpleado, idCapacitacion));
        return Map.of("mensaje", "EmpleadoCapacitacion dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idEmpleado}/{idCapacitacion}/habilitar")
    public EmpleadoCapacitacion habilitar(@PathVariable Integer idEmpleado,
            @PathVariable Integer idCapacitacion) {
        return service.habilitar(new EmpleadoCapacitacionId(idEmpleado, idCapacitacion));
    }

}
