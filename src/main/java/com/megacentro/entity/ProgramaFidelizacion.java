package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla programa_fidelizacion (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "programa_fidelizacion")
public class ProgramaFidelizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Integer idPrograma;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "puntos_por_sol", precision = 6, scale = 2, nullable = false)
    private BigDecimal puntosPorSol = new BigDecimal("1");

    @NotNull
    @Column(name = "vigencia_puntos_meses", nullable = false)
    private Integer vigenciaPuntosMeses = 12;

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotBlank
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "Valores permitidos: ACTIVO, INACTIVO")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVO";

}
