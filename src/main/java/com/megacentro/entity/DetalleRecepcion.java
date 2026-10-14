package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla detalle_recepcion (ms-compras). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_recepcion")
public class DetalleRecepcion {

    @EmbeddedId
    private DetalleRecepcionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idRecepcion")
    @JoinColumn(name = "id_recepcion")
    private RecepcionCompra recepcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Column(name = "cantidad_recibida", nullable = false)
    private Integer cantidadRecibida;

    @NotNull
    @Column(name = "cantidad_rechazada", nullable = false)
    private Integer cantidadRechazada = 0;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
