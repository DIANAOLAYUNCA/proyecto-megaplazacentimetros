package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla incidencia_despacho (ms-despacho). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidencia_despacho")
public class IncidenciaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Integer idIncidencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_despacho", nullable = false)
    @NotNull
    private Despacho despacho;

    @NotBlank
    @Pattern(regexp = "RETRASO|PRODUCTO_DANADO|PRODUCTO_FALTANTE|DIRECCION_ERRONEA|CLIENTE_AUSENTE|OTRO", message = "Valores permitidos: RETRASO, PRODUCTO_DANADO, PRODUCTO_FALTANTE, DIRECCION_ERRONEA, CLIENTE_AUSENTE, OTRO")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotBlank
    @Size(max = 300)
    @Column(name = "descripcion", length = 300, nullable = false)
    private String descripcion;

    @Size(max = 300)
    @Column(name = "accion_correctiva", length = 300)
    private String accionCorrectiva;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @NotBlank
    @Pattern(regexp = "ABIERTA|EN_ATENCION|RESUELTA", message = "Valores permitidos: ABIERTA, EN_ATENCION, RESUELTA")
    @Column(name = "estado", nullable = false)
    private String estado = "ABIERTA";

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
