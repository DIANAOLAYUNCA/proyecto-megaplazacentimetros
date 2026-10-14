package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla detalle_factura_venta (ms-ventas). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_factura_venta")
public class DetalleFacturaVenta {

    @EmbeddedId
    private DetalleFacturaVentaId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idFacturaVenta")
    @JoinColumn(name = "id_factura_venta")
    private FacturaVenta facturaVenta;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @NotNull
    @Column(name = "descuento", precision = 10, scale = 2, nullable = false)
    private BigDecimal descuento = new BigDecimal("0");

    @NotNull
    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
