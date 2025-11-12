package com.ifsp.Sir.Vine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.Sir.Vine.model.Vinho;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class VinhoRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Vinho vinho) {
        em.persist(vinho);
    }

    public List<Vinho> findAll() {
        Query q = em.createQuery("SELECT v FROM Vinho v", Vinho.class);
        List<Vinho> vinhos = q.getResultList();
        return vinhos;
    }

    public Vinho findById(long id) {
        return em.find(Vinho.class, id);
    }

    public List<Vinho> findByname(String nome) {
        Query q = em.createNativeQuery("SELECT * FROM vinho Where nome  LIKE :nome", Vinho.class);
        q.setParameter("nome", "%" + nome + "%");
        List<Vinho> vinhos = q.getResultList();
        return vinhos;
    }

    @Transactional
    public void delete(Long id) {
        Vinho vinho_deletado = em.find(Vinho.class, id);
        em.remove(vinho_deletado);
    }

    @Transactional
    public List<Vinho> update(Vinho vinho) {
        em.merge(vinho);
        List<Vinho> vinhos = findAll();

        return vinhos;

    }

    public int count() {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM vinho");
        return ((Number) q.getSingleResult()).intValue();
    }

    public Vinho randomVinho() {
        int count = count();
        int randomId = (int) (Math.random() * count) + 1;
        System.out.println("achou o vinho " + randomId);
        return findAll().get(randomId - 1);
        
    }

    public int tamanho() {
        Query q = em.createNativeQuery("SELECT COUNT(*) FROM vinho");
        return ((Number) q.getSingleResult()).intValue();
    }
}
