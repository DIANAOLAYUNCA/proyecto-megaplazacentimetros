package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla promocion (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promocion")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promocion")
    private Integer idPromocion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_campana", nullable = false)
    @NotNull
    private Campana campana;

    @NotBlank
    @Size(max = 120)
    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @NotBlank
    @Pattern(regexp = "PORCENTAJE|MONTO_FIJO|2X1", message = "Valores permitidos: PORCENTAJE, MONTO_FIJO, 2X1")
    @Column(name = "tipo_descuento", nullable = false)
    private String tipoDescuento;

    @NotNull
    @Column(name = "valor_descuento", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorDescuento = new BigDecimal("0");

    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @NotBlank
    @Pattern(regexp = "ACTIVA|INACTIVA", message = "Valores permitidos: ACTIVA, INACTIVA")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVA";

}
