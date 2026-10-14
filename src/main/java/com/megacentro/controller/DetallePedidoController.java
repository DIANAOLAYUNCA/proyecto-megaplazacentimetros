package com.megacentro.controller;

import com.megacentro.entity.*;
import com.megacentro.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/** CRUD de detalle_pedido (ms-ventas). */
@RestController
@RequestMapping("/api/detalle-pedido")
@RequiredArgsConstructor
public class DetallePedidoController {

    private final DetallePedidoService service;

    @GetMapping
    public List<DetallePedido> listar() {
        return service.listar();
    }

    @GetMapping("/activos")
    public List<DetallePedido> listarActivos() {
        return service.listarActivos();
    }

    @GetMapping("/{idPedido}/{idProducto}")
    public DetallePedido buscar(@PathVariable Integer idPedido,
            @PathVariable Integer idProducto) {
        return service.buscar(new DetallePedidoId(idPedido, idProducto));
    }

    @PostMapping
    public ResponseEntity<DetallePedido> registrar(@Valid @RequestBody DetallePedido body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(body));
    }

    @PutMapping("/{idPedido}/{idProducto}")
    public DetallePedido actualizar(@PathVariable Integer idPedido,
            @PathVariable Integer idProducto,
            @Valid @RequestBody DetallePedido body) {
        return service.actualizar(new DetallePedidoId(idPedido, idProducto), body);
    }

    /** Eliminación lógica: no borra el registro, solo cambia su estado. */
    @DeleteMapping("/{idPedido}/{idProducto}")
    public Map<String, String> eliminar(@PathVariable Integer idPedido,
            @PathVariable Integer idProducto) {
        service.eliminar(new DetallePedidoId(idPedido, idProducto));
        return Map.of("mensaje", "DetallePedido dado de baja (eliminación lógica)");
    }

    @PatchMapping("/{idPedido}/{idProducto}/habilitar")
    public DetallePedido habilitar(@PathVariable Integer idPedido,
            @PathVariable Integer idProducto) {
        return service.habilitar(new DetallePedidoId(idPedido, idProducto));
    }

}
