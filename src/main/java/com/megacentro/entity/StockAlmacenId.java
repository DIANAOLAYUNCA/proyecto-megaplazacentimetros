package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de stock_almacen. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StockAlmacenId implements Serializable {

    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "id_almacen")
    private Integer idAlmacen;
}
