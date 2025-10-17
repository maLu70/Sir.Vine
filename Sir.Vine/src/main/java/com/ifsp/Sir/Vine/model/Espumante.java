package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "espumante")
public class Espumante extends Produto {

    @Column(name = "teor")
    private String teor;

    @Column(name = "volume")
    private String volume;

    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "pressao")
    private String pressao;

    public Espumante() {
    }

    public Espumante(String teor, String volume, String tipo, String pressao) {
        this.teor = teor;
        this.volume = volume;
        this.tipo = tipo;
        this.pressao = pressao;
    }

    public Espumante(String descricao, String nome, Double preco, String img, String cidade, String ano, String pais,
            String teor, String volume, String tipo, String pressao, int estoque, String tipo_do_produto) {
        super(descricao, nome, preco, img, cidade, ano, pais, estoque, tipo_do_produto);
        this.teor = teor;
        this.volume = volume;
        this.tipo = tipo;
        this.pressao = pressao;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
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

    public String gettipo() {
        return tipo;
    }

    public void settipo(String tipo) {
        this.tipo = tipo;
    }

}
