package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla promocion_producto (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promocion_producto")
public class PromocionProducto {

    @EmbeddedId
    private PromocionProductoId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idPromocion")
    @JoinColumn(name = "id_promocion")
    private Promocion promocion;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
