-- NOTA (proyecto Spring Boot): se agregó la columna  activo TINYINT(1) NOT NULL DEFAULT 1
-- a las tablas sin estado de baja lógica, para poder aplicar eliminación lógica.
-- Tablas afectadas: cargo, asistencia, capacitacion, permiso, rol_permiso, categoria, marca, tipo_empaque, stock_almacen, alerta_stock, detalle_traslado, detalle_orden_compra, recepcion_compra, detalle_recepcion, tipo_cliente, detalle_pedido, detalle_factura_venta, pago_cliente, pago_proveedor, comprobante_pago, transporte, detalle_despacho, guia_despacho, incidencia_despacho, segmento_cliente, promocion_producto, puntos_cliente

-- =====================================================================
-- INTEGRACIÓN DE APLICACIONES - PROCESO DE APRENDIZAJE 1
-- Base de datos integral: PLAZA MEGACENTÍMETROS S.A.C.
-- Gestor: MySQL 8.x
-- 50 tablas organizadas por microservicio:
--   ms-seguridad, ms-personal, ms-almacenes, ms-inventario, ms-compras,
--   ms-ventas, ms-pagos, ms-despacho, ms-marketing
-- =====================================================================

DROP DATABASE IF EXISTS bd_plaza_megacentimetros;
CREATE DATABASE bd_plaza_megacentimetros
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE bd_plaza_megacentimetros;

-- =====================================================================
-- MS-ALMACENES (parte 1): SUCURSAL
-- Se crea primero porque empleado y almacén dependen de ella
-- =====================================================================
CREATE TABLE sucursal (
  id_sucursal      INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(100) NOT NULL,
  direccion        VARCHAR(200) NOT NULL,
  distrito         VARCHAR(80),
  ciudad           VARCHAR(80)  NOT NULL,
  telefono         VARCHAR(20),
  estado           ENUM('ACTIVA','INACTIVA') NOT NULL DEFAULT 'ACTIVA',
  fecha_registro   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- =====================================================================
-- MS-PERSONAL
-- =====================================================================
CREATE TABLE cargo (
  id_cargo         INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(80) NOT NULL UNIQUE,
  descripcion      VARCHAR(200),
  tipo_personal    ENUM('ADMINISTRATIVO','OPERATIVO') NOT NULL,
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE empleado (
  id_empleado      INT AUTO_INCREMENT PRIMARY KEY,
  dni              CHAR(8)      NOT NULL UNIQUE,
  nombres          VARCHAR(80)  NOT NULL,
  apellidos        VARCHAR(100) NOT NULL,
  fecha_nacimiento DATE,
  telefono         VARCHAR(20),
  correo           VARCHAR(120) UNIQUE,
  direccion        VARCHAR(200),
  fecha_ingreso    DATE NOT NULL,
  id_cargo         INT NOT NULL,
  id_sucursal      INT NOT NULL,
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  CONSTRAINT fk_empleado_cargo    FOREIGN KEY (id_cargo)    REFERENCES cargo(id_cargo),
  CONSTRAINT fk_empleado_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
) ENGINE=InnoDB;

CREATE TABLE asistencia (
  id_asistencia    INT AUTO_INCREMENT PRIMARY KEY,
  id_empleado      INT  NOT NULL,
  fecha            DATE NOT NULL,
  hora_entrada     TIME,
  hora_salida      TIME,
  estado           ENUM('PUNTUAL','TARDANZA','FALTA','JUSTIFICADO') NOT NULL DEFAULT 'PUNTUAL',
  observacion      VARCHAR(200),
  CONSTRAINT fk_asistencia_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
  CONSTRAINT uq_asistencia_dia UNIQUE (id_empleado, fecha),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE capacitacion (
  id_capacitacion  INT AUTO_INCREMENT PRIMARY KEY,
  tema             VARCHAR(150) NOT NULL,
  descripcion      VARCHAR(300),
  instructor       VARCHAR(120),
  fecha_inicio     DATE NOT NULL,
  fecha_fin        DATE NOT NULL,
  horas            INT NOT NULL,
  modalidad        ENUM('PRESENCIAL','VIRTUAL') NOT NULL DEFAULT 'PRESENCIAL',
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE empleado_capacitacion (
  id_empleado      INT NOT NULL,
  id_capacitacion  INT NOT NULL,
  nota             DECIMAL(4,2),
  estado           ENUM('INSCRITO','APROBADO','DESAPROBADO','RETIRADO') NOT NULL DEFAULT 'INSCRITO',
  PRIMARY KEY (id_empleado, id_capacitacion),
  CONSTRAINT fk_empcap_empleado     FOREIGN KEY (id_empleado)     REFERENCES empleado(id_empleado),
  CONSTRAINT fk_empcap_capacitacion FOREIGN KEY (id_capacitacion) REFERENCES capacitacion(id_capacitacion)
) ENGINE=InnoDB;

-- =====================================================================
-- MS-SEGURIDAD
-- =====================================================================
CREATE TABLE rol (
  id_rol           INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(50) NOT NULL UNIQUE,
  descripcion      VARCHAR(200),
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB;

CREATE TABLE permiso (
  id_permiso       INT AUTO_INCREMENT PRIMARY KEY,
  modulo           VARCHAR(50)  NOT NULL,
  accion           VARCHAR(50)  NOT NULL,
  descripcion      VARCHAR(200),
  CONSTRAINT uq_permiso UNIQUE (modulo, accion),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE rol_permiso (
  id_rol           INT NOT NULL,
  id_permiso       INT NOT NULL,
  PRIMARY KEY (id_rol, id_permiso),
  CONSTRAINT fk_rolperm_rol     FOREIGN KEY (id_rol)     REFERENCES rol(id_rol),
  CONSTRAINT fk_rolperm_permiso FOREIGN KEY (id_permiso) REFERENCES permiso(id_permiso),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE usuario (
  id_usuario       INT AUTO_INCREMENT PRIMARY KEY,
  username         VARCHAR(50)  NOT NULL UNIQUE,
  password_hash    VARCHAR(255) NOT NULL,
  id_empleado      INT NOT NULL UNIQUE,
  id_rol           INT NOT NULL,
  ultimo_acceso    DATETIME,
  estado           ENUM('ACTIVO','BLOQUEADO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  fecha_creacion   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_usuario_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
  CONSTRAINT fk_usuario_rol      FOREIGN KEY (id_rol)      REFERENCES rol(id_rol)
) ENGINE=InnoDB;

CREATE TABLE auditoria (
  id_auditoria     BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_usuario       INT NOT NULL,
  modulo           VARCHAR(50)  NOT NULL,
  accion           ENUM('INSERTAR','ACTUALIZAR','ELIMINAR','CONSULTAR','LOGIN','LOGOUT') NOT NULL,
  tabla_afectada   VARCHAR(60),
  id_registro      INT,
  detalle          VARCHAR(500),
  ip_origen        VARCHAR(45),
  fecha_hora       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_auditoria_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- =====================================================================
-- MS-ALMACENES (parte 2)
-- =====================================================================
CREATE TABLE almacen (
  id_almacen       INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(100) NOT NULL,
  tipo             ENUM('CENTRAL','SUCURSAL','TRANSITO') NOT NULL,
  direccion        VARCHAR(200) NOT NULL,
  capacidad_m3     DECIMAL(10,2) NOT NULL,
  id_sucursal      INT NOT NULL,
  estado           ENUM('OPERATIVO','MANTENIMIENTO','CERRADO') NOT NULL DEFAULT 'OPERATIVO',
  CONSTRAINT fk_almacen_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal)
) ENGINE=InnoDB;

-- =====================================================================
-- MS-INVENTARIO
-- =====================================================================
CREATE TABLE categoria (
  id_categoria     INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(80) NOT NULL UNIQUE,
  descripcion      VARCHAR(200),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE marca (
  id_marca         INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(80) NOT NULL UNIQUE,
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE tipo_empaque (
  id_tipo_empaque  INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(50) NOT NULL UNIQUE,
  unidades_por_empaque INT NOT NULL DEFAULT 1,
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE producto (
  id_producto      INT AUTO_INCREMENT PRIMARY KEY,
  codigo           VARCHAR(20)  NOT NULL UNIQUE,
  nombre           VARCHAR(150) NOT NULL,
  descripcion      VARCHAR(300),
  id_categoria     INT NOT NULL,
  id_marca         INT NOT NULL,
  id_tipo_empaque  INT NOT NULL,
  precio_compra    DECIMAL(10,2) NOT NULL,
  precio_venta     DECIMAL(10,2) NOT NULL,
  estado           ENUM('ACTIVO','DESCONTINUADO') NOT NULL DEFAULT 'ACTIVO',
  CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria)    REFERENCES categoria(id_categoria),
  CONSTRAINT fk_producto_marca     FOREIGN KEY (id_marca)        REFERENCES marca(id_marca),
  CONSTRAINT fk_producto_empaque   FOREIGN KEY (id_tipo_empaque) REFERENCES tipo_empaque(id_tipo_empaque),
  CONSTRAINT chk_producto_precios  CHECK (precio_compra >= 0 AND precio_venta >= 0)
) ENGINE=InnoDB;

CREATE TABLE stock_almacen (
  id_producto      INT NOT NULL,
  id_almacen       INT NOT NULL,
  cantidad         INT NOT NULL DEFAULT 0,
  stock_minimo     INT NOT NULL DEFAULT 10,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_producto, id_almacen),
  CONSTRAINT fk_stock_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  CONSTRAINT fk_stock_almacen  FOREIGN KEY (id_almacen)  REFERENCES almacen(id_almacen),
  CONSTRAINT chk_stock_cantidad CHECK (cantidad >= 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE movimiento_inventario (
  id_movimiento    BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_producto      INT NOT NULL,
  id_almacen       INT NOT NULL,
  tipo_movimiento  ENUM('ENTRADA','SALIDA') NOT NULL,
  motivo           ENUM('COMPRA','VENTA','TRASLADO','AJUSTE','DEVOLUCION') NOT NULL,
  cantidad         INT NOT NULL,
  documento_ref    VARCHAR(30),
  id_empleado      INT NOT NULL,
  fecha            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_mov_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  CONSTRAINT fk_mov_almacen  FOREIGN KEY (id_almacen)  REFERENCES almacen(id_almacen),
  CONSTRAINT fk_mov_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
  CONSTRAINT chk_mov_cantidad CHECK (cantidad > 0)
) ENGINE=InnoDB;

CREATE TABLE alerta_stock (
  id_alerta        INT AUTO_INCREMENT PRIMARY KEY,
  id_producto      INT NOT NULL,
  id_almacen       INT NOT NULL,
  stock_actual     INT NOT NULL,
  stock_minimo     INT NOT NULL,
  fecha_alerta     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('PENDIENTE','ATENDIDA') NOT NULL DEFAULT 'PENDIENTE',
  CONSTRAINT fk_alerta_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  CONSTRAINT fk_alerta_almacen  FOREIGN KEY (id_almacen)  REFERENCES almacen(id_almacen),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- Traslados (ms-almacenes): dependen de almacén, producto y empleado
CREATE TABLE traslado (
  id_traslado      INT AUTO_INCREMENT PRIMARY KEY,
  id_almacen_origen  INT NOT NULL,
  id_almacen_destino INT NOT NULL,
  id_empleado      INT NOT NULL,
  fecha_salida     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_llegada    DATETIME,
  motivo           VARCHAR(200),
  estado           ENUM('PENDIENTE','EN_TRANSITO','RECIBIDO','ANULADO') NOT NULL DEFAULT 'PENDIENTE',
  CONSTRAINT fk_traslado_origen   FOREIGN KEY (id_almacen_origen)  REFERENCES almacen(id_almacen),
  CONSTRAINT fk_traslado_destino  FOREIGN KEY (id_almacen_destino) REFERENCES almacen(id_almacen),
  CONSTRAINT fk_traslado_empleado FOREIGN KEY (id_empleado)        REFERENCES empleado(id_empleado),
  CONSTRAINT chk_traslado_almacenes CHECK (id_almacen_origen <> id_almacen_destino)
) ENGINE=InnoDB;

CREATE TABLE detalle_traslado (
  id_traslado      INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT NOT NULL,
  PRIMARY KEY (id_traslado, id_producto),
  CONSTRAINT fk_dettras_traslado FOREIGN KEY (id_traslado) REFERENCES traslado(id_traslado),
  CONSTRAINT fk_dettras_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  CONSTRAINT chk_dettras_cantidad CHECK (cantidad > 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- =====================================================================
-- MS-COMPRAS
-- =====================================================================
CREATE TABLE proveedor (
  id_proveedor     INT AUTO_INCREMENT PRIMARY KEY,
  ruc              CHAR(11)     NOT NULL UNIQUE,
  razon_social     VARCHAR(150) NOT NULL,
  contacto         VARCHAR(100),
  telefono         VARCHAR(20),
  correo           VARCHAR(120),
  direccion        VARCHAR(200),
  calificacion     TINYINT CHECK (calificacion BETWEEN 1 AND 5),
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB;

CREATE TABLE orden_compra (
  id_orden_compra  INT AUTO_INCREMENT PRIMARY KEY,
  numero           VARCHAR(20) NOT NULL UNIQUE,
  id_proveedor     INT NOT NULL,
  id_empleado      INT NOT NULL,
  id_almacen_destino INT NOT NULL,
  fecha_emision    DATE NOT NULL,
  fecha_entrega_esperada DATE,
  condiciones_entrega VARCHAR(200),
  total            DECIMAL(12,2) NOT NULL DEFAULT 0,
  estado           ENUM('EMITIDA','APROBADA','RECIBIDA_PARCIAL','RECIBIDA','ANULADA') NOT NULL DEFAULT 'EMITIDA',
  CONSTRAINT fk_oc_proveedor FOREIGN KEY (id_proveedor)       REFERENCES proveedor(id_proveedor),
  CONSTRAINT fk_oc_empleado  FOREIGN KEY (id_empleado)        REFERENCES empleado(id_empleado),
  CONSTRAINT fk_oc_almacen   FOREIGN KEY (id_almacen_destino) REFERENCES almacen(id_almacen)
) ENGINE=InnoDB;

CREATE TABLE detalle_orden_compra (
  id_orden_compra  INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT NOT NULL,
  precio_unitario  DECIMAL(10,2) NOT NULL,
  subtotal         DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id_orden_compra, id_producto),
  CONSTRAINT fk_detoc_orden    FOREIGN KEY (id_orden_compra) REFERENCES orden_compra(id_orden_compra),
  CONSTRAINT fk_detoc_producto FOREIGN KEY (id_producto)     REFERENCES producto(id_producto),
  CONSTRAINT chk_detoc_cantidad CHECK (cantidad > 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE recepcion_compra (
  id_recepcion     INT AUTO_INCREMENT PRIMARY KEY,
  id_orden_compra  INT NOT NULL,
  id_almacen       INT NOT NULL,
  id_empleado      INT NOT NULL,
  fecha_recepcion  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  observacion      VARCHAR(300),
  CONSTRAINT fk_rec_orden    FOREIGN KEY (id_orden_compra) REFERENCES orden_compra(id_orden_compra),
  CONSTRAINT fk_rec_almacen  FOREIGN KEY (id_almacen)      REFERENCES almacen(id_almacen),
  CONSTRAINT fk_rec_empleado FOREIGN KEY (id_empleado)     REFERENCES empleado(id_empleado),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE detalle_recepcion (
  id_recepcion     INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad_recibida INT NOT NULL,
  cantidad_rechazada INT NOT NULL DEFAULT 0,
  PRIMARY KEY (id_recepcion, id_producto),
  CONSTRAINT fk_detrec_recepcion FOREIGN KEY (id_recepcion) REFERENCES recepcion_compra(id_recepcion),
  CONSTRAINT fk_detrec_producto  FOREIGN KEY (id_producto)  REFERENCES producto(id_producto),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE factura_proveedor (
  id_factura_proveedor INT AUTO_INCREMENT PRIMARY KEY,
  serie_numero     VARCHAR(20) NOT NULL,
  id_proveedor     INT NOT NULL,
  id_orden_compra  INT NOT NULL,
  fecha_emision    DATE NOT NULL,
  fecha_vencimiento DATE NOT NULL,
  subtotal         DECIMAL(12,2) NOT NULL,
  igv              DECIMAL(12,2) NOT NULL,
  total            DECIMAL(12,2) NOT NULL,
  estado           ENUM('PENDIENTE','PAGADA_PARCIAL','PAGADA','ANULADA') NOT NULL DEFAULT 'PENDIENTE',
  CONSTRAINT uq_factprov UNIQUE (id_proveedor, serie_numero),
  CONSTRAINT fk_factprov_proveedor FOREIGN KEY (id_proveedor)    REFERENCES proveedor(id_proveedor),
  CONSTRAINT fk_factprov_orden     FOREIGN KEY (id_orden_compra) REFERENCES orden_compra(id_orden_compra)
) ENGINE=InnoDB;

-- =====================================================================
-- MS-VENTAS
-- =====================================================================
CREATE TABLE tipo_cliente (
  id_tipo_cliente  INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(50) NOT NULL UNIQUE,
  descripcion      VARCHAR(200),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE cliente (
  id_cliente       INT AUTO_INCREMENT PRIMARY KEY,
  tipo_documento   ENUM('DNI','RUC','CE') NOT NULL,
  numero_documento VARCHAR(11)  NOT NULL UNIQUE,
  nombres_razon_social VARCHAR(150) NOT NULL,
  telefono         VARCHAR(20),
  correo           VARCHAR(120),
  direccion        VARCHAR(200),
  id_tipo_cliente  INT NOT NULL,
  fecha_registro   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
  CONSTRAINT fk_cliente_tipo FOREIGN KEY (id_tipo_cliente) REFERENCES tipo_cliente(id_tipo_cliente)
) ENGINE=InnoDB;

CREATE TABLE pedido (
  id_pedido        INT AUTO_INCREMENT PRIMARY KEY,
  numero           VARCHAR(20) NOT NULL UNIQUE,
  id_cliente       INT NOT NULL,
  id_empleado      INT NOT NULL,
  id_sucursal      INT NOT NULL,
  id_almacen       INT NOT NULL,
  fecha_pedido     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  subtotal         DECIMAL(12,2) NOT NULL DEFAULT 0,
  descuento        DECIMAL(12,2) NOT NULL DEFAULT 0,
  total            DECIMAL(12,2) NOT NULL DEFAULT 0,
  estado           ENUM('REGISTRADO','CONFIRMADO','FACTURADO','DESPACHADO','ENTREGADO','ANULADO') NOT NULL DEFAULT 'REGISTRADO',
  CONSTRAINT fk_pedido_cliente  FOREIGN KEY (id_cliente)  REFERENCES cliente(id_cliente),
  CONSTRAINT fk_pedido_empleado FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
  CONSTRAINT fk_pedido_sucursal FOREIGN KEY (id_sucursal) REFERENCES sucursal(id_sucursal),
  CONSTRAINT fk_pedido_almacen  FOREIGN KEY (id_almacen)  REFERENCES almacen(id_almacen)
) ENGINE=InnoDB;

CREATE TABLE detalle_pedido (
  id_pedido        INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT NOT NULL,
  precio_unitario  DECIMAL(10,2) NOT NULL,
  subtotal         DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id_pedido, id_producto),
  CONSTRAINT fk_detped_pedido   FOREIGN KEY (id_pedido)   REFERENCES pedido(id_pedido),
  CONSTRAINT fk_detped_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  CONSTRAINT chk_detped_cantidad CHECK (cantidad > 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE factura_venta (
  id_factura_venta INT AUTO_INCREMENT PRIMARY KEY,
  tipo_comprobante ENUM('FACTURA','BOLETA') NOT NULL,
  serie            CHAR(4)  NOT NULL,
  numero           INT      NOT NULL,
  id_pedido        INT NOT NULL UNIQUE,
  id_cliente       INT NOT NULL,
  fecha_emision    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  subtotal         DECIMAL(12,2) NOT NULL,
  igv              DECIMAL(12,2) NOT NULL,
  total            DECIMAL(12,2) NOT NULL,
  estado           ENUM('EMITIDA','PAGADA','ANULADA') NOT NULL DEFAULT 'EMITIDA',
  CONSTRAINT uq_factventa UNIQUE (serie, numero),
  CONSTRAINT fk_factventa_pedido  FOREIGN KEY (id_pedido)  REFERENCES pedido(id_pedido),
  CONSTRAINT fk_factventa_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
) ENGINE=InnoDB;

CREATE TABLE detalle_factura_venta (
  id_factura_venta INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT NOT NULL,
  precio_unitario  DECIMAL(10,2) NOT NULL,
  descuento        DECIMAL(10,2) NOT NULL DEFAULT 0,
  subtotal         DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id_factura_venta, id_producto),
  CONSTRAINT fk_detfv_factura  FOREIGN KEY (id_factura_venta) REFERENCES factura_venta(id_factura_venta),
  CONSTRAINT fk_detfv_producto FOREIGN KEY (id_producto)      REFERENCES producto(id_producto),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- =====================================================================
-- MS-PAGOS
-- =====================================================================
CREATE TABLE metodo_pago (
  id_metodo_pago   INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(50) NOT NULL UNIQUE,
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB;

CREATE TABLE pago_cliente (
  id_pago_cliente  INT AUTO_INCREMENT PRIMARY KEY,
  id_factura_venta INT NOT NULL,
  id_metodo_pago   INT NOT NULL,
  monto            DECIMAL(12,2) NOT NULL,
  fecha_pago       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('PENDIENTE','EN_PROCESO','REALIZADO','RECHAZADO') NOT NULL DEFAULT 'PENDIENTE',
  CONSTRAINT fk_pagocli_factura FOREIGN KEY (id_factura_venta) REFERENCES factura_venta(id_factura_venta),
  CONSTRAINT fk_pagocli_metodo  FOREIGN KEY (id_metodo_pago)   REFERENCES metodo_pago(id_metodo_pago),
  CONSTRAINT chk_pagocli_monto CHECK (monto > 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE pago_proveedor (
  id_pago_proveedor INT AUTO_INCREMENT PRIMARY KEY,
  id_factura_proveedor INT NOT NULL,
  id_metodo_pago   INT NOT NULL,
  monto            DECIMAL(12,2) NOT NULL,
  fecha_pago       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('PENDIENTE','EN_PROCESO','REALIZADO','RECHAZADO') NOT NULL DEFAULT 'PENDIENTE',
  CONSTRAINT fk_pagoprov_factura FOREIGN KEY (id_factura_proveedor) REFERENCES factura_proveedor(id_factura_proveedor),
  CONSTRAINT fk_pagoprov_metodo  FOREIGN KEY (id_metodo_pago)       REFERENCES metodo_pago(id_metodo_pago),
  CONSTRAINT chk_pagoprov_monto CHECK (monto > 0),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE comprobante_pago (
  id_comprobante   INT AUTO_INCREMENT PRIMARY KEY,
  tipo_pago        ENUM('CLIENTE','PROVEEDOR') NOT NULL,
  id_pago_cliente  INT NULL,
  id_pago_proveedor INT NULL,
  numero_operacion VARCHAR(50) NOT NULL,
  fecha_emision    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  archivo_url      VARCHAR(255),
  CONSTRAINT fk_comp_pagocli  FOREIGN KEY (id_pago_cliente)   REFERENCES pago_cliente(id_pago_cliente),
  CONSTRAINT fk_comp_pagoprov FOREIGN KEY (id_pago_proveedor) REFERENCES pago_proveedor(id_pago_proveedor),
  CONSTRAINT chk_comp_origen CHECK (
    (tipo_pago = 'CLIENTE'   AND id_pago_cliente IS NOT NULL AND id_pago_proveedor IS NULL) OR
    (tipo_pago = 'PROVEEDOR' AND id_pago_proveedor IS NOT NULL AND id_pago_cliente IS NULL)
  ),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- =====================================================================
-- MS-DESPACHO
-- =====================================================================
CREATE TABLE transporte (
  id_transporte    INT AUTO_INCREMENT PRIMARY KEY,
  placa            VARCHAR(10) NOT NULL UNIQUE,
  tipo             ENUM('CAMION','FURGONETA','MOTO','TERCERIZADO') NOT NULL,
  capacidad_kg     DECIMAL(10,2) NOT NULL,
  id_conductor     INT,
  estado           ENUM('DISPONIBLE','EN_RUTA','MANTENIMIENTO') NOT NULL DEFAULT 'DISPONIBLE',
  CONSTRAINT fk_transporte_conductor FOREIGN KEY (id_conductor) REFERENCES empleado(id_empleado),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE despacho (
  id_despacho      INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido        INT NOT NULL UNIQUE,
  id_almacen       INT NOT NULL,
  id_empleado      INT NOT NULL,
  id_transporte    INT NOT NULL,
  direccion_entrega VARCHAR(200) NOT NULL,
  fecha_despacho   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fecha_entrega    DATETIME,
  estado           ENUM('PREPARACION','EN_RUTA','ENTREGADO','CON_INCIDENCIA','ANULADO') NOT NULL DEFAULT 'PREPARACION',
  CONSTRAINT fk_despacho_pedido     FOREIGN KEY (id_pedido)     REFERENCES pedido(id_pedido),
  CONSTRAINT fk_despacho_almacen    FOREIGN KEY (id_almacen)    REFERENCES almacen(id_almacen),
  CONSTRAINT fk_despacho_empleado   FOREIGN KEY (id_empleado)   REFERENCES empleado(id_empleado),
  CONSTRAINT fk_despacho_transporte FOREIGN KEY (id_transporte) REFERENCES transporte(id_transporte)
) ENGINE=InnoDB;

CREATE TABLE detalle_despacho (
  id_despacho      INT NOT NULL,
  id_producto      INT NOT NULL,
  cantidad         INT NOT NULL,
  verificado       BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id_despacho, id_producto),
  CONSTRAINT fk_detdesp_despacho FOREIGN KEY (id_despacho) REFERENCES despacho(id_despacho),
  CONSTRAINT fk_detdesp_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE guia_despacho (
  id_guia          INT AUTO_INCREMENT PRIMARY KEY,
  serie_numero     VARCHAR(20) NOT NULL UNIQUE,
  id_despacho      INT NOT NULL UNIQUE,
  punto_partida    VARCHAR(200) NOT NULL,
  punto_llegada    VARCHAR(200) NOT NULL,
  motivo_traslado  VARCHAR(100) NOT NULL DEFAULT 'VENTA',
  peso_total_kg    DECIMAL(10,2),
  fecha_emision    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_guia_despacho FOREIGN KEY (id_despacho) REFERENCES despacho(id_despacho),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE incidencia_despacho (
  id_incidencia    INT AUTO_INCREMENT PRIMARY KEY,
  id_despacho      INT NOT NULL,
  tipo             ENUM('RETRASO','PRODUCTO_DANADO','PRODUCTO_FALTANTE','DIRECCION_ERRONEA','CLIENTE_AUSENTE','OTRO') NOT NULL,
  descripcion      VARCHAR(300) NOT NULL,
  accion_correctiva VARCHAR(300),
  fecha_registro   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('ABIERTA','EN_ATENCION','RESUELTA') NOT NULL DEFAULT 'ABIERTA',
  CONSTRAINT fk_incid_despacho FOREIGN KEY (id_despacho) REFERENCES despacho(id_despacho),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- =====================================================================
-- MS-MARKETING
-- =====================================================================
CREATE TABLE segmento_cliente (
  id_segmento      INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(80) NOT NULL UNIQUE,
  criterio         VARCHAR(300),
  id_tipo_cliente  INT,
  CONSTRAINT fk_segmento_tipocli FOREIGN KEY (id_tipo_cliente) REFERENCES tipo_cliente(id_tipo_cliente),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE campana (
  id_campana       INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(120) NOT NULL,
  objetivo         VARCHAR(300),
  id_segmento      INT,
  fecha_inicio     DATE NOT NULL,
  fecha_fin        DATE NOT NULL,
  presupuesto      DECIMAL(12,2),
  estado           ENUM('PLANIFICADA','ACTIVA','FINALIZADA','CANCELADA') NOT NULL DEFAULT 'PLANIFICADA',
  CONSTRAINT fk_campana_segmento FOREIGN KEY (id_segmento) REFERENCES segmento_cliente(id_segmento),
  CONSTRAINT chk_campana_fechas CHECK (fecha_fin >= fecha_inicio)
) ENGINE=InnoDB;

CREATE TABLE promocion (
  id_promocion     INT AUTO_INCREMENT PRIMARY KEY,
  id_campana       INT NOT NULL,
  nombre           VARCHAR(120) NOT NULL,
  tipo_descuento   ENUM('PORCENTAJE','MONTO_FIJO','2X1') NOT NULL,
  valor_descuento  DECIMAL(10,2) NOT NULL DEFAULT 0,
  fecha_inicio     DATE NOT NULL,
  fecha_fin        DATE NOT NULL,
  estado           ENUM('ACTIVA','INACTIVA') NOT NULL DEFAULT 'ACTIVA',
  CONSTRAINT fk_promocion_campana FOREIGN KEY (id_campana) REFERENCES campana(id_campana)
) ENGINE=InnoDB;

CREATE TABLE promocion_producto (
  id_promocion     INT NOT NULL,
  id_producto      INT NOT NULL,
  PRIMARY KEY (id_promocion, id_producto),
  CONSTRAINT fk_promprod_promocion FOREIGN KEY (id_promocion) REFERENCES promocion(id_promocion),
  CONSTRAINT fk_promprod_producto  FOREIGN KEY (id_producto)  REFERENCES producto(id_producto),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;

CREATE TABLE programa_fidelizacion (
  id_programa      INT AUTO_INCREMENT PRIMARY KEY,
  nombre           VARCHAR(100) NOT NULL,
  puntos_por_sol   DECIMAL(6,2) NOT NULL DEFAULT 1,
  vigencia_puntos_meses INT NOT NULL DEFAULT 12,
  fecha_inicio     DATE NOT NULL,
  estado           ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB;

CREATE TABLE recompensa (
  id_recompensa    INT AUTO_INCREMENT PRIMARY KEY,
  id_programa      INT NOT NULL,
  nombre           VARCHAR(120) NOT NULL,
  descripcion      VARCHAR(300),
  puntos_requeridos INT NOT NULL,
  stock            INT NOT NULL DEFAULT 0,
  estado           ENUM('DISPONIBLE','AGOTADA','INACTIVA') NOT NULL DEFAULT 'DISPONIBLE',
  CONSTRAINT fk_recompensa_programa FOREIGN KEY (id_programa) REFERENCES programa_fidelizacion(id_programa),
  CONSTRAINT chk_recompensa_puntos CHECK (puntos_requeridos > 0)
) ENGINE=InnoDB;

CREATE TABLE canje_recompensa (
  id_canje         INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente       INT NOT NULL,
  id_recompensa    INT NOT NULL,
  puntos_usados    INT NOT NULL,
  fecha_canje      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado           ENUM('SOLICITADO','ENTREGADO','ANULADO') NOT NULL DEFAULT 'SOLICITADO',
  CONSTRAINT fk_canje_cliente    FOREIGN KEY (id_cliente)    REFERENCES cliente(id_cliente),
  CONSTRAINT fk_canje_recompensa FOREIGN KEY (id_recompensa) REFERENCES recompensa(id_recompensa)
) ENGINE=InnoDB;

CREATE TABLE puntos_cliente (
  id_movimiento_puntos INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente       INT NOT NULL,
  id_programa      INT NOT NULL,
  tipo             ENUM('ACUMULA','CANJEA','VENCE') NOT NULL,
  puntos           INT NOT NULL,
  id_factura_venta INT NULL,
  id_canje         INT NULL,
  fecha            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_puntos_cliente  FOREIGN KEY (id_cliente)       REFERENCES cliente(id_cliente),
  CONSTRAINT fk_puntos_programa FOREIGN KEY (id_programa)      REFERENCES programa_fidelizacion(id_programa),
  CONSTRAINT fk_puntos_factura  FOREIGN KEY (id_factura_venta) REFERENCES factura_venta(id_factura_venta),
  CONSTRAINT fk_puntos_canje    FOREIGN KEY (id_canje)         REFERENCES canje_recompensa(id_canje),
  activo           TINYINT(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB;


-- =====================================================================
-- DATOS DE PRUEBA
-- =====================================================================

-- ms-almacenes / ms-personal
INSERT INTO sucursal (nombre, direccion, distrito, ciudad, telefono) VALUES
('Sede Principal', 'Av. Los Innovadores N.º 1250, Parque Industrial Tecnológico', 'Ate', 'Lima', '014501250'),
('Sucursal Norte', 'Av. Túpac Amaru 3450', 'Comas', 'Lima', '015372210'),
('Sucursal Sur', 'Av. Pachacútec 2100', 'Villa El Salvador', 'Lima', '012876543');

INSERT INTO cargo (nombre, descripcion, tipo_personal) VALUES
('Gerente General', 'Dirige la empresa', 'ADMINISTRATIVO'),
('Jefe de Almacén', 'Responsable de un almacén', 'OPERATIVO'),
('Vendedor', 'Atiende pedidos de clientes', 'OPERATIVO'),
('Asistente de Compras', 'Gestiona órdenes de compra', 'ADMINISTRATIVO'),
('Conductor', 'Realiza la entrega de despachos', 'OPERATIVO'),
('Contador', 'Controla pagos y facturación', 'ADMINISTRATIVO');

INSERT INTO empleado (dni, nombres, apellidos, fecha_nacimiento, telefono, correo, fecha_ingreso, id_cargo, id_sucursal) VALUES
('45123678', 'Carlos',  'Ramírez Soto',   '1985-03-12', '987654321', 'cramirez@megacentimetros.pe', '2018-01-15', 1, 1),
('46234789', 'Lucía',   'Flores Paredes', '1990-07-22', '976543210', 'lflores@megacentimetros.pe',  '2019-05-02', 2, 1),
('47345890', 'Jorge',   'Quispe Mamani',  '1994-11-05', '965432109', 'jquispe@megacentimetros.pe',  '2021-02-10', 3, 1),
('48456901', 'Andrea',  'Torres Vega',    '1996-02-18', '954321098', 'atorres@megacentimetros.pe',  '2022-08-01', 4, 1),
('49567012', 'Miguel',  'Huamán Rojas',   '1988-09-30', '943210987', 'mhuaman@megacentimetros.pe',  '2020-03-16', 5, 1),
('40678123', 'Rosa',    'Chávez León',    '1987-12-01', '932109876', 'rchavez@megacentimetros.pe',  '2018-06-20', 6, 1),
('41789234', 'Diego',   'Salazar Pinto',  '1995-04-14', '921098765', 'dsalazar@megacentimetros.pe', '2023-01-09', 2, 2),
('42890345', 'Valeria', 'Castro Núñez',   '1997-06-25', '910987654', 'vcastro@megacentimetros.pe',  '2023-04-03', 3, 2);

INSERT INTO asistencia (id_empleado, fecha, hora_entrada, hora_salida, estado) VALUES
(2, '2026-09-21', '07:55:00', '17:02:00', 'PUNTUAL'),
(3, '2026-09-21', '08:12:00', '17:00:00', 'TARDANZA'),
(5, '2026-09-21', '07:50:00', '16:45:00', 'PUNTUAL'),
(7, '2026-09-21', NULL, NULL, 'FALTA');

INSERT INTO capacitacion (tema, instructor, fecha_inicio, fecha_fin, horas, modalidad) VALUES
('Manejo seguro de montacargas', 'Ing. Pedro Lozano', '2026-08-04', '2026-08-06', 12, 'PRESENCIAL'),
('Atención al cliente mayorista', 'Lic. Marta Ruiz',  '2026-09-01', '2026-09-03', 9,  'VIRTUAL');

INSERT INTO empleado_capacitacion (id_empleado, id_capacitacion, nota, estado) VALUES
(2, 1, 17.50, 'APROBADO'),
(7, 1, 12.00, 'DESAPROBADO'),
(3, 2, 18.00, 'APROBADO'),
(8, 2, NULL,  'INSCRITO');

-- ms-seguridad
INSERT INTO rol (nombre, descripcion) VALUES
('ADMINISTRADOR', 'Acceso total al sistema'),
('VENDEDOR', 'Registra clientes, pedidos y facturas'),
('ALMACENERO', 'Gestiona inventario, traslados y despachos'),
('COMPRAS', 'Gestiona proveedores y órdenes de compra'),
('FINANZAS', 'Gestiona pagos y comprobantes');

INSERT INTO permiso (modulo, accion) VALUES
('VENTAS','REGISTRAR'), ('VENTAS','ANULAR'),
('INVENTARIO','CONSULTAR'), ('INVENTARIO','AJUSTAR'),
('COMPRAS','REGISTRAR'), ('PAGOS','REGISTRAR'),
('DESPACHO','REGISTRAR'), ('SEGURIDAD','ADMINISTRAR');

INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
(2,1),(2,3),
(3,3),(3,4),(3,7),
(4,3),(4,5),
(5,6);

-- Contraseñas guardadas como hash (ejemplo con SHA2)
INSERT INTO usuario (username, password_hash, id_empleado, id_rol) VALUES
('cramirez', SHA2('Admin2026*', 256),  1, 1),
('lflores',  SHA2('Almacen2026*', 256), 2, 3),
('jquispe',  SHA2('Ventas2026*', 256),  3, 2),
('atorres',  SHA2('Compras2026*', 256), 4, 4),
('rchavez',  SHA2('Finanzas2026*', 256),6, 5);

INSERT INTO auditoria (id_usuario, modulo, accion, tabla_afectada, id_registro, detalle, ip_origen) VALUES
(1, 'SEGURIDAD', 'LOGIN', NULL, NULL, 'Inicio de sesión exitoso', '192.168.1.10'),
(3, 'VENTAS', 'INSERTAR', 'pedido', 1, 'Registro del pedido PED-000001', '192.168.1.25'),
(4, 'COMPRAS', 'INSERTAR', 'orden_compra', 1, 'Registro de la orden OC-000001', '192.168.1.31');

-- ms-almacenes
INSERT INTO almacen (nombre, tipo, direccion, capacidad_m3, id_sucursal) VALUES
('Almacén Central Ate',   'CENTRAL',  'Av. Los Innovadores N.º 1250', 5000.00, 1),
('Almacén Comas',         'SUCURSAL', 'Av. Túpac Amaru 3450',        1200.00, 2),
('Almacén Villa El Salvador', 'SUCURSAL', 'Av. Pachacútec 2100',      900.00, 3);

-- ms-inventario
INSERT INTO categoria (nombre, descripcion) VALUES
('Abarrotes', 'Arroz, azúcar, aceite, fideos'),
('Bebidas', 'Gaseosas, aguas, jugos'),
('Limpieza', 'Detergentes, lejía, jabones'),
('Cuidado personal', 'Shampoo, pasta dental, papel higiénico');

INSERT INTO marca (nombre) VALUES ('Costeño'), ('Primor'), ('Inca Kola'), ('Bolívar'), ('Colgate'), ('Don Vittorio');

INSERT INTO tipo_empaque (nombre, unidades_por_empaque) VALUES
('Unidad', 1), ('Paquete x 6', 6), ('Caja x 12', 12), ('Saco 50 kg', 1);

INSERT INTO producto (codigo, nombre, id_categoria, id_marca, id_tipo_empaque, precio_compra, precio_venta) VALUES
('PRD-0001', 'Arroz extra 5 kg',            1, 1, 1, 18.50, 22.90),
('PRD-0002', 'Aceite vegetal 1 L caja x12', 1, 2, 3, 96.00, 115.00),
('PRD-0003', 'Gaseosa 1.5 L paquete x6',    2, 3, 2, 33.00, 39.90),
('PRD-0004', 'Detergente 2.6 kg',           3, 4, 1, 27.00, 32.50),
('PRD-0005', 'Pasta dental 90 g caja x12',  4, 5, 3, 42.00, 51.00),
('PRD-0006', 'Fideo spaghetti 500 g caja x12', 1, 6, 3, 30.00, 36.00);

INSERT INTO stock_almacen (id_producto, id_almacen, cantidad, stock_minimo) VALUES
(1,1,500,100),(2,1,120,30),(3,1,300,60),(4,1,200,40),(5,1,80,20),(6,1,150,30),
(1,2,90,50),(3,2,40,50),(4,2,60,20),
(1,3,70,40),(2,3,15,20);

INSERT INTO movimiento_inventario (id_producto, id_almacen, tipo_movimiento, motivo, cantidad, documento_ref, id_empleado) VALUES
(1, 1, 'ENTRADA', 'COMPRA',   200, 'OC-000001',  2),
(2, 1, 'ENTRADA', 'COMPRA',   50,  'OC-000001',  2),
(1, 1, 'SALIDA',  'VENTA',    20,  'PED-000001', 2),
(3, 1, 'SALIDA',  'VENTA',    10,  'PED-000001', 2),
(1, 1, 'SALIDA',  'TRASLADO', 30,  'TRA-000001', 2),
(1, 2, 'ENTRADA', 'TRASLADO', 30,  'TRA-000001', 7);

INSERT INTO alerta_stock (id_producto, id_almacen, stock_actual, stock_minimo) VALUES
(3, 2, 40, 50),
(2, 3, 15, 20);

INSERT INTO traslado (id_almacen_origen, id_almacen_destino, id_empleado, fecha_salida, fecha_llegada, motivo, estado) VALUES
(1, 2, 2, '2026-09-20 08:00:00', '2026-09-20 11:30:00', 'Reposición por alta demanda', 'RECIBIDO'),
(1, 3, 2, '2026-09-25 09:00:00', NULL, 'Reposición de aceite', 'EN_TRANSITO');

INSERT INTO detalle_traslado (id_traslado, id_producto, cantidad) VALUES
(1, 1, 30), (1, 3, 20), (2, 2, 25);

-- ms-compras
INSERT INTO proveedor (ruc, razon_social, contacto, telefono, correo, calificacion) VALUES
('20100055237', 'Distribuidora Alimentos del Perú S.A.', 'Luis Mendoza', '014445566', 'ventas@alimentosperu.pe', 5),
('20331066703', 'Corporación Bebidas Andinas S.A.C.',  'Karla Ríos',   '016667788', 'pedidos@bebidasandinas.pe', 4),
('20512345678', 'Higiene y Limpieza Total E.I.R.L.',   'Óscar Medina', '017778899', 'contacto@limpiezatotal.pe', 4);

INSERT INTO orden_compra (numero, id_proveedor, id_empleado, id_almacen_destino, fecha_emision, fecha_entrega_esperada, condiciones_entrega, total, estado) VALUES
('OC-000001', 1, 4, 1, '2026-09-15', '2026-09-18', 'Entrega en almacén central, crédito 30 días', 8500.00, 'RECIBIDA'),
('OC-000002', 2, 4, 1, '2026-09-22', '2026-09-26', 'Entrega en almacén central, contado',       3300.00, 'APROBADA');

INSERT INTO detalle_orden_compra (id_orden_compra, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 200, 18.50, 3700.00),
(1, 2, 50,  96.00, 4800.00),
(2, 3, 100, 33.00, 3300.00);

INSERT INTO recepcion_compra (id_orden_compra, id_almacen, id_empleado, fecha_recepcion, observacion) VALUES
(1, 1, 2, '2026-09-18 10:15:00', 'Mercadería conforme');

INSERT INTO detalle_recepcion (id_recepcion, id_producto, cantidad_recibida, cantidad_rechazada) VALUES
(1, 1, 200, 0),
(1, 2, 50, 0);

INSERT INTO factura_proveedor (serie_numero, id_proveedor, id_orden_compra, fecha_emision, fecha_vencimiento, subtotal, igv, total, estado) VALUES
('F001-004521', 1, 1, '2026-09-18', '2026-10-18', 7203.39, 1296.61, 8500.00, 'PAGADA_PARCIAL');

-- ms-ventas
INSERT INTO tipo_cliente (nombre, descripcion) VALUES
('MAYORISTA', 'Compra por volumen para reventa'),
('MINORISTA', 'Compra en menor cantidad para consumo'),
('CORPORATIVO', 'Empresas con contrato de abastecimiento');

INSERT INTO cliente (tipo_documento, numero_documento, nombres_razon_social, telefono, correo, direccion, id_tipo_cliente) VALUES
('RUC', '20601234567', 'Bodega Mi Esperanza E.I.R.L.', '987111222', 'miesperanza@gmail.com', 'Jr. Los Pinos 145, SJL', 1),
('DNI', '71234567',    'María Gonzales Ríos',          '976222333', 'mgonzales@gmail.com',   'Av. Arequipa 2050, Lince', 2),
('RUC', '20458796321', 'Minimarket El Ahorro S.A.C.',  '965333444', 'compras@elahorro.pe',   'Av. Universitaria 880, Los Olivos', 1);

INSERT INTO pedido (numero, id_cliente, id_empleado, id_sucursal, id_almacen, fecha_pedido, subtotal, descuento, total, estado) VALUES
('PED-000001', 1, 3, 1, 1, '2026-09-21 10:30:00', 857.00, 0.00, 857.00, 'ENTREGADO'),
('PED-000002', 2, 3, 1, 1, '2026-09-23 16:00:00', 114.30, 3.60, 110.70, 'FACTURADO');

INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 20, 22.90, 458.00),
(1, 3, 10, 39.90, 399.00),
(2, 1, 2,  22.90, 45.80),
(2, 4, 1,  32.50, 32.50),
(2, 6, 1,  36.00, 36.00);

INSERT INTO factura_venta (tipo_comprobante, serie, numero, id_pedido, id_cliente, fecha_emision, subtotal, igv, total, estado) VALUES
('FACTURA', 'F001', 1, 1, 1, '2026-09-21 10:45:00', 726.27, 130.73, 857.00, 'PAGADA'),
('BOLETA',  'B001', 1, 2, 2, '2026-09-23 16:10:00',  93.81,  16.89, 110.70, 'EMITIDA');

INSERT INTO detalle_factura_venta (id_factura_venta, id_producto, cantidad, precio_unitario, descuento, subtotal) VALUES
(1, 1, 20, 22.90, 0.00, 458.00),
(1, 3, 10, 39.90, 0.00, 399.00),
(2, 1, 2,  22.90, 0.00, 45.80),
(2, 4, 1,  32.50, 0.00, 32.50),
(2, 6, 1,  36.00, 3.60, 32.40);

-- ms-pagos
INSERT INTO metodo_pago (nombre) VALUES ('Efectivo'), ('Tarjeta de crédito/débito'), ('Transferencia bancaria'), ('Yape / Plin');

INSERT INTO pago_cliente (id_factura_venta, id_metodo_pago, monto, fecha_pago, estado) VALUES
(1, 3, 857.00, '2026-09-21 11:00:00', 'REALIZADO'),
(2, 4, 110.70, '2026-09-23 16:12:00', 'EN_PROCESO');

INSERT INTO pago_proveedor (id_factura_proveedor, id_metodo_pago, monto, fecha_pago, estado) VALUES
(1, 3, 4000.00, '2026-09-20 09:00:00', 'REALIZADO');

INSERT INTO comprobante_pago (tipo_pago, id_pago_cliente, id_pago_proveedor, numero_operacion, archivo_url) VALUES
('CLIENTE',   1, NULL, 'TRF-88451230', '/comprobantes/cli/TRF-88451230.pdf'),
('PROVEEDOR', NULL, 1, 'TRF-77120045', '/comprobantes/prov/TRF-77120045.pdf');

-- ms-despacho
INSERT INTO transporte (placa, tipo, capacidad_kg, id_conductor, estado) VALUES
('ABC-123', 'CAMION',    8000.00, 5,    'DISPONIBLE'),
('DEF-456', 'FURGONETA', 1500.00, NULL, 'DISPONIBLE');

INSERT INTO despacho (id_pedido, id_almacen, id_empleado, id_transporte, direccion_entrega, fecha_despacho, fecha_entrega, estado) VALUES
(1, 1, 5, 1, 'Jr. Los Pinos 145, SJL', '2026-09-21 14:00:00', '2026-09-21 16:40:00', 'ENTREGADO');

INSERT INTO detalle_despacho (id_despacho, id_producto, cantidad, verificado) VALUES
(1, 1, 20, TRUE),
(1, 3, 10, TRUE);

INSERT INTO guia_despacho (serie_numero, id_despacho, punto_partida, punto_llegada, peso_total_kg) VALUES
('T001-000001', 1, 'Av. Los Innovadores N.º 1250, Ate', 'Jr. Los Pinos 145, SJL', 190.00);

INSERT INTO incidencia_despacho (id_despacho, tipo, descripcion, accion_correctiva, estado) VALUES
(1, 'RETRASO', 'Tráfico en la Vía de Evitamiento, llegada 40 min tarde', 'Se notificó al cliente y se ajustó la ruta', 'RESUELTA');

-- ms-marketing
INSERT INTO segmento_cliente (nombre, criterio, id_tipo_cliente) VALUES
('Bodegas frecuentes', 'Mayoristas con más de 4 compras al mes', 1),
('Hogares Lima Centro', 'Minoristas de Lima Centro', 2);

INSERT INTO campana (nombre, objetivo, id_segmento, fecha_inicio, fecha_fin, presupuesto, estado) VALUES
('Mes del Abarrote', 'Incrementar ventas de abarrotes a bodegas en 15%', 1, '2026-09-01', '2026-09-30', 5000.00, 'ACTIVA'),
('Fiestas Patrias 2026', 'Impulsar bebidas en julio', 2, '2026-07-01', '2026-07-31', 3000.00, 'FINALIZADA');

INSERT INTO promocion (id_campana, nombre, tipo_descuento, valor_descuento, fecha_inicio, fecha_fin) VALUES
(1, '10% en arroz y fideos', 'PORCENTAJE', 10.00, '2026-09-01', '2026-09-30'),
(2, 'S/ 5 menos en gaseosas x6', 'MONTO_FIJO', 5.00, '2026-07-01', '2026-07-31');

INSERT INTO promocion_producto (id_promocion, id_producto) VALUES
(1, 1), (1, 6), (2, 3);

INSERT INTO programa_fidelizacion (nombre, puntos_por_sol, vigencia_puntos_meses, fecha_inicio) VALUES
('Mega Puntos', 1.00, 12, '2026-01-01');

INSERT INTO recompensa (id_programa, nombre, puntos_requeridos, stock) VALUES
(1, 'Vale de consumo S/ 20', 500, 100),
(1, 'Canasta de abarrotes',  1500, 20);

INSERT INTO canje_recompensa (id_cliente, id_recompensa, puntos_usados, estado) VALUES
(1, 1, 500, 'ENTREGADO');

INSERT INTO puntos_cliente (id_cliente, id_programa, tipo, puntos, id_factura_venta, id_canje) VALUES
(1, 1, 'ACUMULA', 857, 1, NULL),
(2, 1, 'ACUMULA', 110, 2, NULL),
(1, 1, 'CANJEA',  500, NULL, 1);

-- =====================================================================
-- VERIFICACIÓN
-- =====================================================================
SELECT COUNT(*) AS total_tablas
FROM information_schema.tables
WHERE table_schema = 'bd_plaza_megacentimetros';
