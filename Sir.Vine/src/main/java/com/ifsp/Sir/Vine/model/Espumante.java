package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "espumante")
public class Espumante extends Produto {

    public Espumante(){}

    @Column(name = "teor")
    private String teor;

    @Column(name = "volume")
    private String volume;

    @Column(name = "cor")
    private String cor;

    @Column(name = "uva")
    private String uva;


    public Espumante(String descricao, String nome, Double preco, String img, String teor, String volume, String cor, String uva) {
        super(descricao, nome, preco, img);
        this.teor = teor;
        this.volume = volume;
        this.cor = cor;
        this.uva = uva;
    }

    public Espumante(String teor, String volume, String cor, String uva) {
        this.teor = teor;
        this.volume = volume;
        this.cor = cor;
        this.uva = uva;
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
    public String getUva() {
        return uva;
    }
    public void setUva(String uva) {
        this.uva = uva;
    }
}
