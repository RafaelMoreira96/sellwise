package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.ItemCompra;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemCompraDto {
    private Integer idItemCompra;
    private Integer idProduto;
    private String descricao;
    private String codBarras;
    private double precoCompra;
    private double quantidade;
    
    public ItemCompraDto(ItemCompra itemCompra) {
        this.idItemCompra = itemCompra.getIdItemCompra();
        this.idProduto = itemCompra.getIdProduto();
        this.descricao = itemCompra.getDescricao();
        this.codBarras = itemCompra.getCodBarras();
        this.precoCompra = itemCompra.getPrecoCompra();
        this.quantidade = itemCompra.getQuantidade();
    }

    public Integer getIdItemCompra() {
        return idItemCompra;
    }

    public void setIdItemCompra(Integer idItemCompra) {
        this.idItemCompra = idItemCompra;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
