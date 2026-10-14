package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla pago_cliente (ms-pagos). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago_cliente")
public class PagoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_cliente")
    private Integer idPagoCliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_factura_venta", nullable = false)
    @NotNull
    private FacturaVenta facturaVenta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    @NotNull
    private MetodoPago metodoPago;

    @NotNull
    @Column(name = "monto", precision = 12, scale = 2, nullable = false)
    private BigDecimal monto;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @NotBlank
    @Pattern(regexp = "PENDIENTE|EN_PROCESO|REALIZADO|RECHAZADO", message = "Valores permitidos: PENDIENTE, EN_PROCESO, REALIZADO, RECHAZADO")
    @Column(name = "estado", nullable = false)
    private String estado = "PENDIENTE";

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
