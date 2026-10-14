package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;

/** Tabla auditoria (ms-seguridad). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private Long idAuditoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull
    private Usuario usuario;

    @NotBlank
    @Size(max = 50)
    @Column(name = "modulo", length = 50, nullable = false)
    private String modulo;

    @NotBlank
    @Pattern(regexp = "INSERTAR|ACTUALIZAR|ELIMINAR|CONSULTAR|LOGIN|LOGOUT", message = "Valores permitidos: INSERTAR, ACTUALIZAR, ELIMINAR, CONSULTAR, LOGIN, LOGOUT")
    @Column(name = "accion", nullable = false)
    private String accion;

    @Size(max = 60)
    @Column(name = "tabla_afectada", length = 60)
    private String tablaAfectada;

    @Column(name = "id_registro")
    private Integer idRegistro;

    @Size(max = 500)
    @Column(name = "detalle", length = 500)
    private String detalle;

    @Size(max = 45)
    @Column(name = "ip_origen", length = 45)
    private String ipOrigen;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();

}
