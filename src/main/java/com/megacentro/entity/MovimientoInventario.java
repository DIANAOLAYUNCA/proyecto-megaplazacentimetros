package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla movimiento_inventario (ms-inventario). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimiento_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    @NotNull
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen", nullable = false)
    @NotNull
    private Almacen almacen;

    @NotBlank
    @Pattern(regexp = "ENTRADA|SALIDA", message = "Valores permitidos: ENTRADA, SALIDA")
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;

    @NotBlank
    @Pattern(regexp = "COMPRA|VENTA|TRASLADO|AJUSTE|DEVOLUCION", message = "Valores permitidos: COMPRA, VENTA, TRASLADO, AJUSTE, DEVOLUCION")
    @Column(name = "motivo", nullable = false)
    private String motivo;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Size(max = 30)
    @Column(name = "documento_ref", length = 30)
    private String documentoRef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

}
