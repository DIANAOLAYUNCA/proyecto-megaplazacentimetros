package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla asistencia (ms-personal). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Integer idAsistencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @NotBlank
    @Pattern(regexp = "PUNTUAL|TARDANZA|FALTA|JUSTIFICADO", message = "Valores permitidos: PUNTUAL, TARDANZA, FALTA, JUSTIFICADO")
    @Column(name = "estado", nullable = false)
    private String estado = "PUNTUAL";

    @Size(max = 200)
    @Column(name = "observacion", length = 200)
    private String observacion;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
