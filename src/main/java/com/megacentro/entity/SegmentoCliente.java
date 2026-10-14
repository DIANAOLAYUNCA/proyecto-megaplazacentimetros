package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla segmento_cliente (ms-marketing). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "segmento_cliente")
public class SegmentoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_segmento")
    private Integer idSegmento;

    @NotBlank
    @Size(max = 80)
    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Size(max = 300)
    @Column(name = "criterio", length = 300)
    private String criterio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo_cliente")
    private TipoCliente tipoCliente;

    @NotNull
    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

}
