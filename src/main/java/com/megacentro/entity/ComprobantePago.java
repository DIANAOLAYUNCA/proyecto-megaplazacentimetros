package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla comprobante_pago (ms-pagos). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comprobante_pago")
public class ComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comprobante")
    private Integer idComprobante;

    @NotBlank
    @Pattern(regexp = "CLIENTE|PROVEEDOR", message = "Valores permitidos: CLIENTE, PROVEEDOR")
    @Column(name = "tipo_pago", nullable = false)
    private String tipoPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pago_cliente")
    private PagoCliente pagoCliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pago_proveedor")
    private PagoProveedor pagoProveedor;

    @NotBlank
    @Size(max = 50)
    @Column(name = "numero_operacion", length = 50, nullable = false)
    private String numeroOperacion;

    @NotNull
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

    @Size(max = 255)
    @Column(name = "archivo_url", length = 255)
    private String archivoUrl;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
