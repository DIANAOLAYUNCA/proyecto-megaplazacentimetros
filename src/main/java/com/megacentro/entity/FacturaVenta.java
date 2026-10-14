package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla factura_venta (ms-ventas). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura_venta")
public class FacturaVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura_venta")
    private Integer idFacturaVenta;

    @NotBlank
    @Pattern(regexp = "FACTURA|BOLETA", message = "Valores permitidos: FACTURA, BOLETA")
    @Column(name = "tipo_comprobante", nullable = false)
    private String tipoComprobante;

    @NotBlank
    @Size(max = 4)
    @Column(name = "serie", length = 4, nullable = false)
    private String serie;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", nullable = false)
    @NotNull
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Cliente cliente;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

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
    @Pattern(regexp = "EMITIDA|PAGADA|ANULADA", message = "Valores permitidos: EMITIDA, PAGADA, ANULADA")
    @Column(name = "estado", nullable = false)
    private String estado = "EMITIDA";

}
