package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla orden_compra (ms-compras). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orden_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_compra")
    private Integer idOrdenCompra;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero", length = 20, nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    @NotNull
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen_destino", nullable = false)
    @NotNull
    private Almacen almacenDestino;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "fecha_entrega_esperada")
    private LocalDate fechaEntregaEsperada;

    @Size(max = 200)
    @Column(name = "condiciones_entrega", length = 200)
    private String condicionesEntrega;

    @NotNull
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total = new BigDecimal("0");

    @NotBlank
    @Pattern(regexp = "EMITIDA|APROBADA|RECIBIDA_PARCIAL|RECIBIDA|ANULADA", message = "Valores permitidos: EMITIDA, APROBADA, RECIBIDA_PARCIAL, RECIBIDA, ANULADA")
    @Column(name = "estado", nullable = false)
    private String estado = "EMITIDA";

}
