package com.ifsp.Sir.Vine.repository;

import java.util.List;

import com.ifsp.Sir.Vine.model.Queijo;
import com.ifsp.Sir.Vine.model.Vinho;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class QueijoRepositorio {
    @PersistenceContext
    private EntityManager em;


    public List<Queijo> findall(){
        Query q = em.createNativeQuery("SELECT * FROM queijo", Queijo.class);
        List<Queijo> Queijos = q.getResultList();
        return Queijos;
    }

    @Transactional
    public void save(Queijo queijo) {
        em.persist(queijo);
    }


    public Queijo findById(int id) {
        return em.find(Queijo.class, id);
    }

    @Transactional
    public void delete(int id) {
        Queijo queijo_deletado = em.find(Queijo.class, id);
        em.remove(queijo_deletado);
    }

    @Transactional
    public void update(Queijo queijo) {
        em.merge(queijo);
    }

    public int count() {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM queijo");
        return ((Number) q.getSingleResult()).intValue();
    }
    public Queijo randomQueijo() {
        int count = count();
        int randomId = (int) (Math.random() * count) + 1;
        return findall().get(randomId - 1);
    }
}
