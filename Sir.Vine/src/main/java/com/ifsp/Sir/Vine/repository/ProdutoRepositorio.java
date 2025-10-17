package com.ifsp.Sir.Vine.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ifsp.Sir.Vine.model.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class ProdutoRepositorio {
    @PersistenceContext
    private EntityManager em;

    public List<Produto> findAll(){
        Query q = em.createNativeQuery("SELECT * FROM Produto", Produto.class);
        List<Produto> Produtos = q.getResultList();
        return Produtos;
    }

    public Produto findById(long id) {
        return em.find(Produto.class, id);
    }
}
