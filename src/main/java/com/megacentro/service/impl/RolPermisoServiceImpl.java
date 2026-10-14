package com.megacentro.service.impl;

import com.megacentro.common.AbstractCrudService;
import com.megacentro.entity.*;
import com.megacentro.exception.ConflictoException;
import com.megacentro.repository.RolPermisoRepository;
import com.megacentro.service.RolPermisoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolPermisoServiceImpl extends AbstractCrudService<RolPermiso, RolPermisoId> implements RolPermisoService {

    private final RolPermisoRepository repository;

    public RolPermisoServiceImpl(RolPermisoRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<RolPermiso, RolPermisoId> repo() {
        return repository;
    }

    @Override
    protected String nombre() {
        return "RolPermiso";
    }

    @Override
    protected void antesDeRegistrar(RolPermiso e) {
        if (e.getRol() == null || e.getRol().getIdRol() == null
                || e.getPermiso() == null || e.getPermiso().getIdPermiso() == null) {
            throw new IllegalArgumentException("Debe enviar rol.idRol y permiso.idPermiso");
        }
        e.setId(new RolPermisoId(e.getRol().getIdRol(), e.getPermiso().getIdPermiso()));
        if (repository.existsById(e.getId())) {
            throw new ConflictoException("Ya existe un registro de RolPermiso con esa clave compuesta");
        }
    }

    @Override
    protected void asignarId(RolPermiso e, RolPermisoId id) {
        e.setId(id);
        Rol a = new Rol();
        a.setIdRol(id.getIdRol());
        e.setRol(a);
        Permiso b = new Permiso();
        b.setIdPermiso(id.getIdPermiso());
        e.setPermiso(b);
    }

    @Override
    protected boolean tieneBajaLogica() {
        return true;
    }

    @Override
    protected void marcarInactivo(RolPermiso e) {
        e.setActivo(false);
    }

    @Override
    protected void marcarActivo(RolPermiso e) {
        e.setActivo(true);
    }

    @Override
    public List<RolPermiso> listarActivos() {
        return repository.findByActivoTrue();
    }

}
