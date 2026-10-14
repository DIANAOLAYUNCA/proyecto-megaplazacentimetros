package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_orden_compra. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleOrdenCompraId implements Serializable {

    @Column(name = "id_orden_compra")
    private Integer idOrdenCompra;

    @Column(name = "id_producto")
    private Integer idProducto;
}
