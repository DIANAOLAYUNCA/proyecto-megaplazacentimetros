package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetalleRecepcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_recepcion (ms-compras). */
@RestController
@RequestMapping("/api/detalle-recepcion")
@RequiredArgsConstructor
public class DetalleRecepcionController {

    private final DetalleRecepcionService service;

    @GetMapping
    public List<DetalleRecepcion> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetalleRecepcion> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idRecepcion}/{idProducto}")
    public DetalleRecepcion buscar(@PathVariable Integer idRecepcion,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetalleRecepcionId(idRecepcion, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetalleRecepcion> registrar(@Valid @RequestBody DetalleRecepcion body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idRecepcion}/{idProducto}")
    public DetalleRecepcion actualizar(@PathVariable Integer idRecepcion,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetalleRecepcion body) {
        return service.actualizar(new DetalleRecepcionId(idRecepcion, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idRecepcion}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idRecepcion,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetalleRecepcionId(idRecepcion, idProducto));
        return Map.of("mensaje", "DetalleRecepcion dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idRecepcion}/{idProducto}/habilitar")
    public DetalleRecepcion habilitar(@PathVariable Integer idRecepcion,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetalleRecepcionId(idRecepcion, idProducto));
    }

}
