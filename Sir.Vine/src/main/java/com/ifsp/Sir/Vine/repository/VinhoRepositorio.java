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
        String sql = "INSERT INTO vinho (descricao, nome, preco, img, tipo, teor, volume, cidade, pais, ano) VALUES (:descricao, :nome, :preco, :img, :tipo, :teor, :volume, :cidade, :pais, :ano)";

        Query query = em.createNativeQuery(sql);

        query.setParameter("descricao", vinho.getDescricao());
        query.setParameter("nome", vinho.getNome());
        query.setParameter("preco", vinho.getPreco());
        query.setParameter("img", vinho.getImg());
        query.setParameter("teor", vinho.getTeor());
        query.setParameter("tipo", vinho.getTipo());
        query.setParameter("volume", vinho.getVolume());
        query.setParameter("teor", vinho.getTeor());
        query.setParameter("tipo", vinho.getTipo());
        query.setParameter("volume", vinho.getVolume());

        query.executeUpdate();
    }

    public List<Vinho> findAll() {
        Query q = em.createNativeQuery("SELECT * FROM vinho", Vinho.class);

        List<Vinho> vinhos = q.getResultList();

        return vinhos;
    }

    public Vinho findById(int id) {
        String sql = "SELECT * FROM vinho WHERE id = :vid";

        Query q = em.createNativeQuery(sql, Vinho.class);

        q.setParameter("vid", id);
        Vinho vinho = (Vinho) q.getSingleResult();

        return vinho;
    }

    @Transactional
    public void delete(int id) {
        String sql = "DELETE FROM vinho WHERE id = :id";

        System.out.println("deletei");

        Query q = em.createNativeQuery(sql);
        q.setParameter("id", id);

        q.executeUpdate();

    }

    @Transactional
    public List<Vinho> update(Vinho vinho) {

        String sql = "update vinho set descricao = :descricao, nome = :nome, preco = :preco  where id = :id";
        Query query = em.createNativeQuery(sql);

        query.setParameter("descricao", vinho.getDescricao());
        query.setParameter("nome", vinho.getNome());
        query.setParameter("preco", vinho.getPreco());
        query.setParameter("id", vinho.getId());

        query.executeUpdate();

        List<Vinho> vinhos = findAll();

        return vinhos;

    }
}

