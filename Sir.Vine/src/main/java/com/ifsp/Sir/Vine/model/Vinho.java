package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vinho")
public class Vinho extends Produto {


    public Vinho(){}


    @Column(name = "tipo")
    private String tipo;

    @Column(name = "teor")
    private String teor;

    @Column(name = "volume")
    private String volume;

    public Vinho(String descricao, String nome, Double preco, String img, String tipo, String teor, String volume) {
        super(descricao, nome, preco, img);
        this.tipo = tipo;
        this.teor = teor;
        this.volume = volume;
    }

    public Vinho(String tipo, String teor, String volume) {
        this.tipo = tipo;
        this.teor = teor;
        this.volume = volume;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

}
