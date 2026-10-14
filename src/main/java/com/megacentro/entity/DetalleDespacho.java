package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla detalle_despacho (ms-despacho). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_despacho")
public class DetalleDespacho {

    @EmbeddedId
    private DetalleDespachoId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idDespacho")
    @JoinColumn(name = "id_despacho")
    private Despacho despacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
