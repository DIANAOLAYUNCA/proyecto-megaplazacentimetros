package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla tipo_empaque (ms-inventario). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_empaque")
public class TipoEmpaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_empaque")
    private Integer idTipoEmpaque;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "unidades_por_empaque", nullable = false)
    private Integer unidadesPorEmpaque = 1;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
