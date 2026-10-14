package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla empleado_capacitacion (ms-personal). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado_capacitacion")
public class EmpleadoCapacitacion {

    @EmbeddedId
    private EmpleadoCapacitacionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idEmpleado")
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCapacitacion")
    @JoinColumn(name = "id_capacitacion")
    private Capacitacion capacitacion;

    @Column(name = "nota", precision = 4, scale = 2)
    private BigDecimal nota;

    @NotBlank
    @Pattern(regexp = "INSCRITO|APROBADO|DESAPROBADO|RETIRADO", message = "Valores permitidos: INSCRITO, APROBADO, DESAPROBADO, RETIRADO")
    @Column(name = "estado", nullable = false)
    private String estado = "INSCRITO";

}
