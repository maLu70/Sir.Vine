package com.ifsp.Sir.Vine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "queijo")

public class Queijo extends Produto {

    public Queijo(){}

    @Column(name = "leite")
    private String leite;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "peso")
    private String peso;

    @Column(name = "gorgura")
    private String gordura;


    public Queijo(String leite, String tipo, String peso, String gordura) {
        this.leite = leite;
        this.tipo = tipo;
        this.peso = peso;
        this.gordura = gordura;
    }

    public Queijo(String descricao, String nome, Double preco, String img, String leite, String tipo, String peso,
            String gordura) {
        super(descricao, nome, preco, img);
        this.leite = leite;
        this.tipo = tipo;
        this.peso = peso;
        this.gordura = gordura;
    }


    public String getLeite() {
        return leite;
    }

    public void setLeite(String leite) {
        this.leite = leite;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getGordura() {
        return gordura;
    }

    public void setGordura(String gordura) {
        this.gordura = gordura;
    }
    
}
