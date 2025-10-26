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

    public List<Produto> findAll() {

        return em.createQuery("SELECT p FROM Produto p", Produto.class)
                .getResultList();
    }

    public Produto findById(long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> filter(Float min, Float max, String tipo, String pais, String ordem, String nome) {
        String queryString = "SELECT p FROM Produto p WHERE 1=1";

        if (min != null) {
            queryString += " AND p.preco >= :min";
        }
        if (max != null) {
            queryString += " AND p.preco <= :max";
        }
        if (tipo != null && !tipo.isEmpty()) {
            queryString += " AND p.tipo_do_produto = :tipo";
        }
        if (pais != null && !pais.isEmpty()) {
            queryString += " AND p.pais = :pais";
        }
        if (nome != null && !nome.isEmpty()) {
            queryString += " AND p.nome LIKE :nome";
        }

        var q = em.createQuery(queryString, Produto.class);

        if (min != null)
            q.setParameter("min", min);
        if (max != null)
            q.setParameter("max", max);
        if (tipo != null && !tipo.isEmpty())
            q.setParameter("tipo", tipo);
        if (pais != null && !pais.isEmpty())
            q.setParameter("pais", pais);
        if (nome != null && !nome.isEmpty())
            q.setParameter("nome", "%" + nome + "%");

        List<Produto> produtos = q.getResultList();

        produtos.sort((p1, p2) -> {
            switch (ordem) {
                case "az":
                    return p1.getNome().compareToIgnoreCase(p2.getNome());
                case "pCres":
                    return Double.compare(p1.getPreco(), p2.getPreco());
                case "pDecre":
                    return Double.compare(p2.getPreco(), p1.getPreco());

                default:
                    return p1.getNome().compareToIgnoreCase(p2.getNome());
            }
        });
        return produtos;
    }

    public List<String> findAllCountries() {
        Query q = em.createQuery("SELECT DISTINCT p.pais FROM Produto p", String.class);
        List<String> countries = q.getResultList();
        return countries;
    }

}
