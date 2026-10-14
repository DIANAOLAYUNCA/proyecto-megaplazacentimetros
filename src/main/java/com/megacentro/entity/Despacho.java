package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla despacho (ms-despacho). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "despacho")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho")
    private Integer idDespacho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", nullable = false)
    @NotNull
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen", nullable = false)
    @NotNull
    private Almacen almacen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_transporte", nullable = false)
    @NotNull
    private Transporte transporte;

    @NotBlank
    @Size(max = 200)
    @Column(name = "direccion_entrega", length = 200, nullable = false)
    private String direccionEntrega;

    @NotNull
    @Column(name = "fecha_despacho", nullable = false)
    private LocalDateTime fechaDespacho = LocalDateTime.now();

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;

    @NotBlank
    @Pattern(regexp = "PREPARACION|EN_RUTA|ENTREGADO|CON_INCIDENCIA|ANULADO", message = "Valores permitidos: PREPARACION, EN_RUTA, ENTREGADO, CON_INCIDENCIA, ANULADO")
    @Column(name = "estado", nullable = false)
    private String estado = "PREPARACION";

}
