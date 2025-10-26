package com.ifsp.Sir.Vine.repository;

import org.springframework.stereotype.Repository;

import com.ifsp.Sir.Vine.model.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioRepositorio {

    
@PersistenceContext
private EntityManager em;

    public boolean autenticar(String email, String senha) {
        Usuario usuario = em.find(Usuario.class, email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return true;
        }
        return false;        
    }

    public boolean isAdmin(String email) {
        Usuario usuario = em.find(Usuario.class, email);
        if (usuario != null) {
            return usuario.isAdmin();
        }
        return false;
    }

    public Usuario findByEmail(String email) {
        return em.find(Usuario.class, email);
    }

    @Transactional
    public void save(Usuario usuario) {
        em.persist(usuario);
    }

    public void update(Usuario usuario) {
        em.merge(usuario);
    }

    public void delete(Usuario usuario) {
        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
    }  

    public void saver(Usuario usuario) {
        em.persist(usuario);
    }
}
