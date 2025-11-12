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
    public boolean existsByEmail(String email) {
        Usuario usuario = em.find(Usuario.class, email);
        return usuario != null;
    }

    public boolean verificarCPF(String CPF){
        if (CPF == null) return false;

    CPF = CPF.replaceAll("[^0-9]", "");

    
    if (CPF.length() != 11) return false;

    if (CPF.matches("(\\d)\\1{10}")) return false;

    try {
        
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (CPF.charAt(i) - '0') * (10 - i);
        }
        int resto = 11 - (soma % 11);
        char digito1 = (resto == 10 || resto == 11) ? '0' : (char)(resto + '0');

        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (CPF.charAt(i) - '0') * (11 - i);
        }
        resto = 11 - (soma % 11);
        char digito2 = (resto == 10 || resto == 11) ? '0' : (char)(resto + '0');

        return (digito1 == CPF.charAt(9) && digito2 == CPF.charAt(10));
    } catch (Exception e) {
        return false;
    }
    }
}
