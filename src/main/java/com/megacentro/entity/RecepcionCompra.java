package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla recepcion_compra (ms-compras). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recepcion_compra")
public class RecepcionCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recepcion")
    private Integer idRecepcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_orden_compra", nullable = false)
    @NotNull
    private OrdenCompra ordenCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen", nullable = false)
    @NotNull
    private Almacen almacen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @NotNull
    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDateTime fechaRecepcion = LocalDateTime.now();

    @Size(max = 300)
    @Column(name = "observacion", length = 300)
    private String observacion;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
