package com.ifsp.Sir.Vine.model;

public class ItemCarrinho {

    private Long id;
    private String tipo;
    private int qtd;
    private Object produto;

    public ItemCarrinho() {}

    public ItemCarrinho(Long id, String tipo, int qtd) {
        this.id = id;
        this.tipo = tipo;
        this.qtd = qtd;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getQtd() { return qtd; }
    public void setQtd(int qtd) { this.qtd = qtd; }

    public Object getProduto() { return produto; }
    public void setProduto(Object produto) { this.produto = produto; }
}
