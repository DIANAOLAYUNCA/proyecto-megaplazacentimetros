package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetalleDespachoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_despacho (ms-despacho). */
@RestController
@RequestMapping("/api/detalle-despacho")
@RequiredArgsConstructor
public class DetalleDespachoController {

    private final DetalleDespachoService service;

    @GetMapping
    public List<DetalleDespacho> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetalleDespacho> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idDespacho}/{idProducto}")
    public DetalleDespacho buscar(@PathVariable Integer idDespacho,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetalleDespachoId(idDespacho, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetalleDespacho> registrar(@Valid @RequestBody DetalleDespacho body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idDespacho}/{idProducto}")
    public DetalleDespacho actualizar(@PathVariable Integer idDespacho,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetalleDespacho body) {
        return service.actualizar(new DetalleDespachoId(idDespacho, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idDespacho}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idDespacho,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetalleDespachoId(idDespacho, idProducto));
        return Map.of("mensaje", "DetalleDespacho dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idDespacho}/{idProducto}/habilitar")
    public DetalleDespacho habilitar(@PathVariable Integer idDespacho,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetalleDespachoId(idDespacho, idProducto));
    }

}
