package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_pedido. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetallePedidoId implements Serializable {

    @Column(name = "id_pedido")
    private Integer idPedido;

    @Column(name = "id_producto")
    private Integer idProducto;
}
