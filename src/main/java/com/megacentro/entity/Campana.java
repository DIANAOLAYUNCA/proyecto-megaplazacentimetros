package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla campana (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campana")
public class Campana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_campana")
    private Integer idCampana;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @Size(max = 300)
    @Column(name = "objetivo", length = 300)
    private String objetivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_segmento")
    private SegmentoCliente segmento;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "presupuesto", precision = 12, scale = 2)
    private BigDecimal presupuesto;

    @NotBlank
    @Pattern(regexp = "PLANIFICADA|ACTIVA|FINALIZADA|CANCELADA", message = "Valores permitidos: PLANIFICADA, ACTIVA, FINALIZADA, CANCELADA")
    @Column(name = "estado", nullable = false)
    private String estado = "PLANIFICADA";

}
