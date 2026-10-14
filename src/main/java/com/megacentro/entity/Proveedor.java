package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla proveedor (ms-compras). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @NotBlank
    @Size(max = 11)
    @Column(name = "ruc", length = 11, nullable = false)
    private String ruc;

    @NotBlank
    @Size(max = 150)
    @Column(name = "razon_social", length = 150, nullable = false)
    private String razonSocial;

    @Size(max = 100)
    @Column(name = "contacto", length = 100)
    private String contacto;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 120)
    @Column(name = "correo", length = 120)
    private String correo;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "calificacion")
    private Integer calificacion;

    @NotBlank
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "Valores permitidos: ACTIVO, INACTIVO")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVO";

}
