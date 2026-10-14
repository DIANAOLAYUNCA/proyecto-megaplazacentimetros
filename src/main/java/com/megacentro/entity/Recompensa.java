package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla recompensa (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recompensa")
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recompensa")
    private Integer idRecompensa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_programa", nullable = false)
    @NotNull
    private ProgramaFidelizacion programa;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @Size(max = 300)
    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @NotNull
    @Column(name = "puntos_requeridos", nullable = false)
    private Integer puntosRequeridos;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @NotBlank
    @Pattern(regexp = "DISPONIBLE|AGOTADA|INACTIVA", message = "Valores permitidos: DISPONIBLE, AGOTADA, INACTIVA")
    @Column(name = "estado", nullable = false)
    private String estado = "DISPONIBLE";

}
