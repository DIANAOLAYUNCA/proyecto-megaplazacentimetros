package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla canje_recompensa (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "canje_recompensa")
public class CanjeRecompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canje")
    private Integer idCanje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recompensa", nullable = false)
    @NotNull
    private Recompensa recompensa;

    @NotNull
    @Column(name = "puntos_usados", nullable = false)
    private Integer puntosUsados;

    @NotNull
    @Column(name = "fecha_canje", nullable = false)
    private LocalDateTime fechaCanje = LocalDateTime.now();

    @NotBlank
    @Pattern(regexp = "SOLICITADO|ENTREGADO|ANULADO", message = "Valores permitidos: SOLICITADO, ENTREGADO, ANULADO")
    @Column(name = "estado", nullable = false)
    private String estado = "SOLICITADO";

}
