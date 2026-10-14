# Plaza Megacentímetros — API REST (Integración de Aplicaciones)

Proyecto Spring Boot 3.5 / Java 21 / MySQL 8 que expone un CRUD completo de las **50 tablas** de `bd_plaza_megacentimetros` (PA1), con **eliminación lógica**.

## Estructura

Misma organización por capas que el proyecto UNFPG de clase:

```
com.megacentro
├── entity        # 50 entidades JPA (+ 10 clases *Id para claves compuestas)
├── repository    # JpaRepository por entidad
├── service       # interfaz por entidad
├── service.impl  # implementación por entidad
├── controller    # @RestController por entidad
├── common        # CrudService / AbstractCrudService (lógica CRUD compartida)
└── exception     # errores uniformes (400, 404, 405, 409)
```

## Cómo ejecutarlo

1. Crear la base de datos con el script (ejecutar completo en MySQL Workbench):
   `src/main/resources/db/bd_plaza_megacentimetros.sql`
2. Configurar usuario y contraseña (por defecto `root` sin contraseña) con variables de entorno `DB_USER` y `DB_PASSWORD`, o editando `application.properties`.
3. Abrir el proyecto en IntelliJ / STS y ejecutar `MegacentroApplication`, o por consola: `mvn spring-boot:run`.
4. API en `http://localhost:8080/api/...`

## Endpoints (por cada tabla)

| Método | Ruta | Acción |
|---|---|---|
| GET | `/api/{tabla}` | listar todos |
| GET | `/api/{tabla}/activos` | listar no dados de baja |
| GET | `/api/{tabla}/{id}` | buscar |
| POST | `/api/{tabla}` | registrar (201) |
| PUT | `/api/{tabla}/{id}` | actualizar |
| DELETE | `/api/{tabla}/{id}` | **eliminación lógica** |
| PATCH | `/api/{tabla}/{id}/habilitar` | reactivar |

`{tabla}` es el nombre de la tabla con guiones: `/api/cliente`, `/api/stock-almacen`, `/api/detalle-pedido`, etc.

- **Claves compuestas** (10 tablas de detalle): `/api/detalle-pedido/{idPedido}/{idProducto}`. En el POST se envían las relaciones, no el `id`.
- **Relaciones**: se envían como objeto con su id, por ejemplo `"cliente": { "idCliente": 1 }`. La respuesta devuelve la relación completa.
- **Errores**: `{ timestamp, status, error, message, detalles }`. 400 validación, 404 no existe, 405 operación no permitida, 409 duplicado/FK/restricción.

## Eliminación lógica

Nunca se hace `DELETE` físico. Según la tabla:

| Estrategia | Tablas | Qué hace DELETE / PATCH habilitar |
|---|---|---|
| `estado` existente (21) | sucursal, empleado, usuario, almacen, producto, proveedor, orden_compra, pedido, factura_venta, … | cambia `estado` a `INACTIVO/ANULADO/ANULADA/CANCELADA/CERRADO/DESCONTINUADO/RETIRADO` y de vuelta al valor por defecto |
| columna `activo` **nueva** (27) | cargo, categoria, detalle_*, rol_permiso, … | pone `activo` en 0 / 1 |
| ninguna (2) | `auditoria`, `movimiento_inventario` | son bitácoras: no se pueden eliminar (405) |

> ⚠️ **Cambio al modelo:** el script incluido en este proyecto **agrega `activo TINYINT(1) NOT NULL DEFAULT 1`** a las 27 tablas que no tenían un estado de baja (la lista está en la cabecera del `.sql`). Si el grupo acuerda otro modelo final, hay que mantener esa columna o ajustar el servicio.

## Seguridad básica

- `usuario.passwordHash`: se recibe en claro en el POST/PUT, se guarda como **SHA-256** (igual que `SHA2(...,256)` del script) y **nunca se devuelve** en las respuestas. Para producción se debería usar BCrypt con Spring Security.

## Postman

Importar `postman/Plaza-Megacentimetros.postman_collection.json`. Trae una carpeta por microservicio y una subcarpeta por tabla, con Registrar, Buscar, Actualizar, Listar, Listar activos, Eliminar lógico y Habilitar. Las variables `{{rnd}}`, `{{rnd8}}`, `{{rnd11}}` se regeneran en cada petición para evitar duplicados.

- Ejecutar primero las carpetas de maestros (ms-almacenes, ms-personal, ms-inventario…) y después las que dependen de ellas; los ids se guardan solos tras cada POST.
- Los cuerpos de ejemplo usan id `1` para las relaciones, que existen si se cargó el script con sus datos de prueba.
- Las tablas de detalle (clave compuesta) y las de relación única pueden dar **409** si el par de ids ya existe en los datos de prueba: cambiar el id en el cuerpo.

## Estado de verificación

- Compila y arranca; los 50 mapeos JPA son válidos.
- Probado con HTTP real (sobre H2 en memoria): POST/GET/PUT/DELETE/PATCH, clave compuesta, validaciones, 404/405/409.
- **No probado contra MySQL real** ni se ejecutó la colección de Postman completa.
