package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla traslado (ms-almacenes). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "traslado")
public class Traslado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_traslado")
    private Integer idTraslado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen_origen", nullable = false)
    @NotNull
    private Almacen almacenOrigen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen_destino", nullable = false)
    @NotNull
    private Almacen almacenDestino;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida = LocalDateTime.now();

    @Column(name = "fecha_llegada")
    private LocalDateTime fechaLlegada;

    @Size(max = 200)
    @Column(name = "motivo", length = 200)
    private String motivo;

    @NotBlank
    @Pattern(regexp = "PENDIENTE|EN_TRANSITO|RECIBIDO|ANULADO", message = "Valores permitidos: PENDIENTE, EN_TRANSITO, RECIBIDO, ANULADO")
    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";

}
