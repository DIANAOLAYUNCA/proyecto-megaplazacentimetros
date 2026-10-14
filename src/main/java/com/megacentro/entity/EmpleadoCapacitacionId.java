package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de empleado_capacitacion. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmpleadoCapacitacionId implements Serializable {

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_capacitacion")
    private Integer idCapacitacion;
}
