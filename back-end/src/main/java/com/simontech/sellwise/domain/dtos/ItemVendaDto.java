package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.ItemVenda;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemVendaDto {
    private Integer idItemVenda;
    private Integer idProduto;
    private String descricao;
    private String codBarras;
    private double precoVenda;
    private double quantidade;
    private double desconto;

    public ItemVendaDto(ItemVenda itemVenda) {
        this.idItemVenda = itemVenda.getIdItemVenda();
        this.idProduto = itemVenda.getIdProduto();
        this.descricao = itemVenda.getDescricao();
        this.codBarras = itemVenda.getCodBarras();
        this.precoVenda = itemVenda.getPrecoVendido();
        this.quantidade = itemVenda.getQuantidade();
        this.desconto = itemVenda.getDesconto();
    }

    public Integer getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(Integer idItemVenda) {
        this.idItemVenda = idItemVenda;
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

    public double getPrecoVendido() {
        return precoVenda;
    }

    public void setPrecoVendido(double precoVendido) {
        this.precoVenda = precoVendido;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
