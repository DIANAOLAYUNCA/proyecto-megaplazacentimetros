package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla almacen (ms-almacenes). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer idAlmacen;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotBlank
    @Pattern(regexp = "CENTRAL|SUCURSAL|TRANSITO", message = "Valores permitidos: CENTRAL, SUCURSAL, TRANSITO")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotBlank
    @Size(max = 200)
    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "capacidad_m3", precision = 10, scale = 2, nullable = false)
    private BigDecimal capacidadM3;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @NotNull
    private Sucursal sucursal;

    @NotBlank
    @Pattern(regexp = "OPERATIVO|MANTENIMIENTO|CERRADO", message = "Valores permitidos: OPERATIVO, MANTENIMIENTO, CERRADO")
    @Column(name = "estado", nullable = false)
    private String estado = "OPERATIVO";

}
