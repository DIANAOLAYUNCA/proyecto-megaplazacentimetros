package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla pedido (ms-ventas). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero", length = 20, nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal", nullable = false)
    @NotNull
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_almacen", nullable = false)
    @NotNull
    private Almacen almacen;

    @NotNull
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @NotNull
    @Column(name = "subtotal", precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal = new BigDecimal("0");

    @NotNull
    @Column(name = "descuento", precision = 12, scale = 2, nullable = false)
    private BigDecimal descuento = new BigDecimal("0");

    @NotNull
    @Column(name = "total", precision = 12, scale = 2, nullable = false)
    private BigDecimal total = new BigDecimal("0");

    @NotBlank
    @Pattern(regexp = "REGISTRADO|CONFIRMADO|FACTURADO|DESPACHADO|ENTREGADO|ANULADO", message = "Valores permitidos: REGISTRADO, CONFIRMADO, FACTURADO, DESPACHADO, ENTREGADO, ANULADO")
    @Column(name = "estado", nullable = false)
    private String estado = "REGISTRADO";

}
