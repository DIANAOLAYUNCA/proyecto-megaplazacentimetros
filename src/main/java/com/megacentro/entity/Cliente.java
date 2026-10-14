package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla cliente (ms-ventas). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @NotBlank
    @Pattern(regexp = "DNI|RUC|CE", message = "Valores permitidos: DNI, RUC, CE")
    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @NotBlank
    @Size(max = 11)
    @Column(name = "numero_documento", length = 11, nullable = false)
    private String numeroDocumento;

    @NotBlank
    @Size(max = 150)
    @Column(name = "nombres_razon_social", length = 150, nullable = false)
    private String nombresRazonSocial;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 120)
    @Column(name = "correo", length = 120)
    private String correo;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_cliente", nullable = false)
    @NotNull
    private TipoCliente tipoCliente;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @NotBlank
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "Valores permitidos: ACTIVO, INACTIVO")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVO";

}
