package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla stock_almacen (ms-inventario). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_almacen")
public class StockAlmacen {

    @EmbeddedId
    private StockAlmacenId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idAlmacen")
    @JoinColumn(name = "id_almacen")
    private Almacen almacen;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 0;

    @NotNull
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo = 10;

    @NotNull
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
