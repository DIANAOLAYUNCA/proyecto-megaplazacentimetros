package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_factura_venta. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleFacturaVentaId implements Serializable {

    @Column(name = "id_factura_venta")
    private Integer idFacturaVenta;

    @Column(name = "id_producto")
    private Integer idProducto;
}
