package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "espumante")
public class Espumante extends Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEspumante;

    @Column(name = "teor")
    private String teor;

    @Column(name = "volume")
    private String volume;

    @Column(name = "cor")
    private String cor;

    public Espumante(String descricao, String nome, Double preco, String img, String teor, String volume, String cor) {
        super(descricao, nome, preco, img);
        this.teor = teor;
        this.volume = volume;
        this.cor = cor;
    }

    public Espumante(String teor, String volume, String cor) {
        this.teor = teor;
        this.volume = volume;
        this.cor = cor;
    }

    public long getIdEspumante() {
        return idEspumante;
    }

    public void setIdEspumante(long idEspumante) {
        this.idEspumante = idEspumante;
    }

    public String getTeor() {
        return teor;
    }

    public void setTeor(String teor) {
        this.teor = teor;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    
    
}
