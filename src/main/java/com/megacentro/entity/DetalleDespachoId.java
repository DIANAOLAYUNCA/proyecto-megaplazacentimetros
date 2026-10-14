package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de detalle_despacho. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleDespachoId implements Serializable {

    @Column(name = "id_despacho")
    private Integer idDespacho;

    @Column(name = "id_producto")
    private Integer idProducto;
}
