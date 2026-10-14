package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla factura_proveedor (ms-compras). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura_proveedor")
public class FacturaProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura_proveedor")
    private Integer idFacturaProveedor;

    @NotBlank
    @Size(max = 20)
    @Column(name = "serie_numero", length = 20, nullable = false)
    private String serieNumero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    @NotNull
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_orden_compra", nullable = false)
    @NotNull
    private OrdenCompra ordenCompra;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull
    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "igv", precision = 12, scale = 2, nullable = false)
    private BigDecimal igv;

    @NotNull
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total;

    @NotBlank
    @Pattern(regexp = "PENDIENTE|PAGADA_PARCIAL|PAGADA|ANULADA", message = "Valores permitidos: PENDIENTE, PAGADA_PARCIAL, PAGADA, ANULADA")
    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";

}
