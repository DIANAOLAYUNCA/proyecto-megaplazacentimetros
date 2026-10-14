package com.megacentro.common;

import java.util.List;

/** Operaciones CRUD comunes a todos los servicios. */
public interface CrudService<T, ID> {

    List<T> listar();

    /** Registros que no están dados de baja lógica. */
    List<T> listarActivos();

    T buscar(ID id);

    T registrar(T entidad);

    T actualizar(ID id, T entidad);

    /** Eliminación lógica (nunca borra físicamente). */
    void eliminar(ID id);

    T habilitar(ID id);
}
