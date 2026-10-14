package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla capacitacion (ms-personal). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "capacitacion")
public class Capacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_capacitacion")
    private Integer idCapacitacion;

    @NotBlank
    @Size(max = 150)
    @Column(name = "tema", length = 150, nullable = false)
    private String tema;

    @Size(max = 300)
    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Size(max = 120)
    @Column(name = "instructor", length = 120)
    private String instructor;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotNull
    @Column(name = "horas", nullable = false)
    private Integer horas;

    @NotBlank
    @Pattern(regexp = "PRESENCIAL|VIRTUAL", message = "Valores permitidos: PRESENCIAL, VIRTUAL")
    @Column(name = "modalidad", nullable = false)
    private String modalidad = "PRESENCIAL";

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
