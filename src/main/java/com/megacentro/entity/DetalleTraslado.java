package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla detalle_traslado (ms-almacenes). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_traslado")
public class DetalleTraslado {

    @EmbeddedId
    private DetalleTrasladoId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idTraslado")
    @JoinColumn(name = "id_traslado")
    private Traslado traslado;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
