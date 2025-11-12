package com.ifsp.Sir.Vine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.Sir.Vine.model.Queijo;
import com.ifsp.Sir.Vine.model.Vinho;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class QueijoRepositorio {
    @PersistenceContext
    private EntityManager em;


    public List<Queijo> findall(){
         Query q = em.createQuery("SELECT v FROM Queijo v", Queijo.class);
        List<Queijo> queijos = q.getResultList();
        return queijos;
    }

    @Transactional
    public void save(Queijo queijo) {
        em.persist(queijo);
    }


    public Queijo findById(long id) {
        return em.find(Queijo.class, id);
    }

    @Transactional
    public void delete(Long id) {
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
    public int tamanho() {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM queijo");
        return ((Number) q.getSingleResult()).intValue();
    }
}
