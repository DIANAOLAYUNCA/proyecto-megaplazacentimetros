package com.megacentro.common;

import com.megacentro.exception.OperacionNoPermitidaException;
import com.megacentro.exception.RecursoNoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Implementación base: cada servicio concreto solo define lo específico de su entidad. */
@Transactional
public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    @PersistenceContext
    private EntityManager em;

    protected abstract JpaRepository<T, ID> repo();

    protected abstract String nombre();

    /** Prepara la entidad antes de insertar (id nulo o clave compuesta). */
    protected abstract void antesDeRegistrar(T entidad);

    /** Fuerza el id de la ruta sobre la entidad recibida. */
    protected abstract void asignarId(T entidad, ID id);

    protected void antesDeActualizar(T nueva, T existente) {
    }

    protected boolean tieneBajaLogica() {
        return false;
    }

    protected void marcarInactivo(T entidad) {
        throw new OperacionNoPermitidaException(nombre() + " no admite eliminación lógica");
    }

    protected void marcarActivo(T entidad) {
        throw new OperacionNoPermitidaException(nombre() + " no admite habilitación");
    }

    /** Guarda y recarga la entidad para devolver las relaciones completas (no solo el id enviado). */
    private T guardarYRecargar(T entidad) {
        T guardada = repo().save(entidad);
        em.flush();
        em.refresh(guardada);
        return guardada;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> listar() {
        return repo().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> listarActivos() {
        return repo().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T buscar(ID id) {
        return repo().findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(nombre() + " no encontrado: " + id));
    }

    @Override
    public T registrar(T entidad) {
        antesDeRegistrar(entidad);
        return guardarYRecargar(entidad);
    }

    @Override
    public T actualizar(ID id, T entidad) {
        T existente = buscar(id);
        asignarId(entidad, id);
        antesDeActualizar(entidad, existente);
        return guardarYRecargar(entidad);
    }

    @Override
    public void eliminar(ID id) {
        if (!tieneBajaLogica()) {
            throw new OperacionNoPermitidaException(nombre() + " no admite eliminación");
        }
        T entidad = buscar(id);
        marcarInactivo(entidad);
        repo().save(entidad);
    }

    @Override
    public T habilitar(ID id) {
        if (!tieneBajaLogica()) {
            throw new OperacionNoPermitidaException(nombre() + " no admite habilitación");
        }
        T entidad = buscar(id);
        marcarActivo(entidad);
        return guardarYRecargar(entidad);
    }
}
