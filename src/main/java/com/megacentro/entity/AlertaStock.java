package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla alerta_stock (ms-inventario). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerta_stock")
public class AlertaStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    @NotNull
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen", nullable = false)
    @NotNull
    private Almacen almacen;

    @NotNull
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @NotNull
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @NotNull
    @Column(name = "fecha_alerta", nullable = false)
    private LocalDateTime fechaAlerta = LocalDateTime.now();

    @NotBlank
    @Pattern(regexp = "PENDIENTE|ATENDIDA", message = "Valores permitidos: PENDIENTE, ATENDIDA")
    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
