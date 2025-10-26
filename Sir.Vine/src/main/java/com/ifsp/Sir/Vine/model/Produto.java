package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "img")
    private String img;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "ano")
    private String ano;

    @Column(name = "pais")
    private String pais;

    @Column(name = "estoque")
    private Integer estoque;

    @Column(name = "tipo_do_produto")
    private String tipo_do_produto;

    public Produto() {
    }

    public Produto(String descricao, String nome, Double preco, String img, String cidade, String ano, String pais, Integer estoque, String tipo_do_produto) {
        this.descricao = descricao;
        this.nome = nome;
        this.preco = preco;
        this.img = img;
        this.cidade = cidade;
        this.ano = ano;
        this.pais = pais;
        this.estoque = estoque;
        this.tipo_do_produto = tipo_do_produto;

    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getTipo_do_produto() {
        return tipo_do_produto;
    }

    public void setTipo_do_produto(String tipo_do_produto) {
        this.tipo_do_produto = tipo_do_produto;
    }
}
