package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla guia_despacho (ms-despacho). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guia_despacho")
public class GuiaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_guia")
    private Integer idGuia;

    @NotBlank
    @Size(max = 20)
    @Column(name = "serie_numero", length = 20, nullable = false)
    private String serieNumero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_despacho", nullable = false)
    @NotNull
    private Despacho despacho;

    @NotBlank
    @Size(max = 200)
    @Column(name = "punto_partida", length = 200, nullable = false)
    private String puntoPartida;

    @NotBlank
    @Size(max = 200)
    @Column(name = "punto_llegada", length = 200, nullable = false)
    private String puntoLlegada;

    @NotBlank
    @Size(max = 100)
    @Column(name = "motivo_traslado", length = 100, nullable = false)
    private String motivoTraslado = "VENTA";

    @Column(name = "peso_total_kg", precision = 10, scale = 2)
    private BigDecimal pesoTotalKg;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
