package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetalleTrasladoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_traslado (ms-almacenes). */
@RestController
@RequestMapping("/api/detalle-traslado")
@RequiredArgsConstructor
public class DetalleTrasladoController {

    private final DetalleTrasladoService service;

    @GetMapping
    public List<DetalleTraslado> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetalleTraslado> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idTraslado}/{idProducto}")
    public DetalleTraslado buscar(@PathVariable Integer idTraslado,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetalleTrasladoId(idTraslado, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetalleTraslado> registrar(@Valid @RequestBody DetalleTraslado body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idTraslado}/{idProducto}")
    public DetalleTraslado actualizar(@PathVariable Integer idTraslado,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetalleTraslado body) {
        return service.actualizar(new DetalleTrasladoId(idTraslado, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idTraslado}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idTraslado,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetalleTrasladoId(idTraslado, idProducto));
        return Map.of("mensaje", "DetalleTraslado dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idTraslado}/{idProducto}/habilitar")
    public DetalleTraslado habilitar(@PathVariable Integer idTraslado,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetalleTrasladoId(idTraslado, idProducto));
    }

}
