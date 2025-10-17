package com.ifsp.Sir.Vine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.Sir.Vine.model.Espumante;
import com.ifsp.Sir.Vine.model.Vinho;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class EspumanteRepositorio {
    @PersistenceContext
    private EntityManager em;

    public List<Espumante> findAll(){
        Query q = em.createQuery("SELECT v FROM Espumante v", Espumante.class);
        List<Espumante> espumantes = q.getResultList();
        return espumantes;
    }
    
    @Transactional
    public void save(Espumante espumante) {
        em.persist(espumante);
    }

    public Espumante findById(long id) {
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

    public Espumante randomEspumante() {
        int count = count();
        int randomId = (int) (Math.random() * count) + 1;
        return findAll().get(randomId - 1);
    }
}
