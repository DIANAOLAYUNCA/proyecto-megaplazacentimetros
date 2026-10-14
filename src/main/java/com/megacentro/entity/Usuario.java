package com.megacentro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Tabla usuario (ms-seguridad). */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank
    @Size(max = 50)
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 255)
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    @NotNull
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    @NotNull
    private Rol rol;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @NotBlank
    @Pattern(regexp = "ACTIVO|BLOQUEADO|INACTIVO", message = "Valores permitidos: ACTIVO, BLOQUEADO, INACTIVO")
    @Column(name = "estado", nullable = false)
    private String estado = "ACTIVO";

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

}
