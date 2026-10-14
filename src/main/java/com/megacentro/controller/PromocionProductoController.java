package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.PromocionProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de promocion_producto (ms-marketing). */
@RestController
@RequestMapping("/api/promocion-producto")
@RequiredArgsConstructor
public class PromocionProductoController {

    private final PromocionProductoService service;

    @GetMapping
    public List<PromocionProducto> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<PromocionProducto> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idPromocion}/{idProducto}")
    public PromocionProducto buscar(@PathVariable Integer idPromocion,
            @PathVariable Integer idProducto) {
        return service.buscar(new PromocionProductoId(idPromocion, idProducto));
    }

    @PostMapping
    public ResponseEntity<PromocionProducto> registrar(@Valid @RequestBody PromocionProducto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idPromocion}/{idProducto}")
    public PromocionProducto actualizar(@PathVariable Integer idPromocion,
            @PathVariable Integer idProducto,
            @Valid @RequestBody PromocionProducto body) {
        return service.actualizar(new PromocionProductoId(idPromocion, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idPromocion}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idPromocion,
            @PathVariable Integer idProducto) {
        service.eliminar(new PromocionProductoId(idPromocion, idProducto));
        return Map.of("mensaje", "PromocionProducto dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idPromocion}/{idProducto}/habilitar")
    public PromocionProducto habilitar(@PathVariable Integer idPromocion,
            @PathVariable Integer idProducto) {
        return service.habilitar(new PromocionProductoId(idPromocion, idProducto));
    }

}
