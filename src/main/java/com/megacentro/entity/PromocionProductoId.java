package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de promocion_producto. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PromocionProductoId implements Serializable {

    @Column(name = "id_promocion")
    private Integer idPromocion;

    @Column(name = "id_producto")
    private Integer idProducto;
}
