package com.ifsp.Sir.Vine.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "compra")
public class Compra {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "cep")
    private String cep;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "Carrinho")
    String carrinho;

    @Column(name = "frete")
    private Float frete;

    public Compra(LocalDateTime data, String cep, String endereco, String carrinho, Float frete) {
        this.data = data;
        this.cep = cep;
        this.endereco = endereco;
        this.carrinho = carrinho;
        this.frete = frete;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(String carrinho) {
        this.carrinho = carrinho;
    }

    public Float getFrete() {
        return frete;
    }

    public void setFrete(Float frete) {
        this.frete = frete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
