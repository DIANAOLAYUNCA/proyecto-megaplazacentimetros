package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.StockAlmacenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de stock_almacen (ms-inventario). */
@RestController
@RequestMapping("/api/stock-almacen")
@RequiredArgsConstructor
public class StockAlmacenController {

    private final StockAlmacenService service;

    @GetMapping
    public List<StockAlmacen> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<StockAlmacen> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idProducto}/{idAlmacen}")
    public StockAlmacen buscar(@PathVariable Integer idProducto,
            @PathVariable Integer idAlmacen) {
        return service.buscar(new StockAlmacenId(idProducto, idAlmacen));
    }

    @PostMapping
    public ResponseEntity<StockAlmacen> registrar(@Valid @RequestBody StockAlmacen body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idProducto}/{idAlmacen}")
    public StockAlmacen actualizar(@PathVariable Integer idProducto,
            @PathVariable Integer idAlmacen,
            @Valid @RequestBody StockAlmacen body) {
        return service.actualizar(new StockAlmacenId(idProducto, idAlmacen), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idProducto}/{idAlmacen}")
    public Map<String, String> eliminar(@PathVariable Integer idProducto,
            @PathVariable Integer idAlmacen) {
        service.eliminar(new StockAlmacenId(idProducto, idAlmacen));
        return Map.of("mensaje", "StockAlmacen dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idProducto}/{idAlmacen}/habilitar")
    public StockAlmacen habilitar(@PathVariable Integer idProducto,
            @PathVariable Integer idAlmacen) {
        return service.habilitar(new StockAlmacenId(idProducto, idAlmacen));
    }

}
