package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_recepcion. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleRecepcionId implements Serializable {

    @Column(name = "id_recepcion")
    private Integer idRecepcion;

    @Column(name = "id_producto")
    private Integer idProducto;
}
