package com.ifsp.Sir.Vine.repository;

import java.util.List;

import com.ifsp.Sir.Vine.model.Espumante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class EspumanteRepositorio {
    @PersistenceContext
    private EntityManager em;

    public List<Espumante> findAll(){
        Query q = em.createNativeQuery("SELECT * FROM espumante", Espumante.class);
        List<Espumante> Espumantes = q.getResultList();
        return Espumantes;
    }
    
    @Transactional
    public void save(Espumante espumante) {
        em.persist(espumante);
    }

    public Espumante findById(int id) {
        return em.find(Espumante.class, id);
    }

    @Transactional
    public void delete(int id) {
        Espumante espumante_deletado = em.find(Espumante.class, id);
        em.remove(espumante_deletado);
    }

    @Transactional
    public void update(Espumante espumante) {
        em.merge(espumante);
    }

    public int count() {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM espumante");
        return ((Number) q.getSingleResult()).intValue();
    }

    public Espumante randoEspumante() {
        int count = count();
        int randomId = (int) (Math.random() * count) + 1;
        return findAll().get(randomId - 1);
    }
}
