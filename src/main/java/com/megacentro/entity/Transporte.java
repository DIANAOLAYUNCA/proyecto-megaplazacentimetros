package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla transporte (ms-despacho). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transporte")
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transporte")
    private Integer idTransporte;

    @NotBlank
    @Size(max = 10)
    @Column(name = "placa", length = 10, nullable = false)
    private String placa;

    @NotBlank
    @Pattern(regexp = "CAMION|FURGONETA|MOTO|TERCERIZADO", message = "Valores permitidos: CAMION, FURGONETA, MOTO, TERCERIZADO")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "capacidad_kg", precision = 10, scale = 2, nullable = false)
    private BigDecimal capacidadKg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_conductor")
    private Empleado conductor;

    @NotBlank
    @Pattern(regexp = "DISPONIBLE|EN_RUTA|MANTENIMIENTO", message = "Valores permitidos: DISPONIBLE, EN_RUTA, MANTENIMIENTO")
    @Column(name = "estado", nullable = false)
    private String estado = "DISPONIBLE";

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
