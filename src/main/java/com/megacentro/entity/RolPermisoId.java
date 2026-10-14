package com.megacentro.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/** Clave compuesta de rol_permiso. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolPermisoId implements Serializable {

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "id_permiso")
    private Integer idPermiso;
}
