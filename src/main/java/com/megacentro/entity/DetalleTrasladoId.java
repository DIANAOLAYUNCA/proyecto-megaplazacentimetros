package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_traslado. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleTrasladoId implements Serializable {

    @Column(name = "id_traslado")
    private Integer idTraslado;

    @Column(name = "id_producto")
    private Integer idProducto;
}
