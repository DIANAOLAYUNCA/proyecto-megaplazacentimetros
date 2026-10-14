package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla sucursal (ms-almacenes). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sucursal")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 200)
    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;

    @Size(max = 80)
    @Column(name = "distrito", length = 80)
    private String distrito;

    @NotBlank
    @Size(max = 80)
    @Column(name = "ciudad", length = 80, nullable = false)
    private String ciudad;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @NotBlank
    @Pattern(regexp = "ACTIVA|INACTIVA", message = "Valores permitidos: ACTIVA, INACTIVA")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVA";

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

}
