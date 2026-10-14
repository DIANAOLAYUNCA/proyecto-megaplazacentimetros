package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla puntos_cliente (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "puntos_cliente")
public class PuntosCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_puntos")
    private Integer idMovimientoPuntos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_programa", nullable = false)
    @NotNull
    private ProgramaFidelizacion programa;

    @NotBlank
    @Pattern(regexp = "ACUMULA|CANJEA|VENCE", message = "Valores permitidos: ACUMULA, CANJEA, VENCE")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_factura_venta")
    private FacturaVenta facturaVenta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_canje")
    private CanjeRecompensa canje;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
