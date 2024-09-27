package com.simontech.sellwise.domain.dtos;

import java.util.Optional;
import com.simontech.sellwise.domain.Produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Produto DTO", description = "Objeto de transferência de dados para Produto")
public class ProdutoDto {
    private Integer idProduto;

    @NotNull(message = "O campo 'DESCRIÇÃO' precisa ser preenchido")
    private String descricao;

    @NotNull(message = "O campo 'CÓDIGO DE BARRAS' precisa ser preenchido")
    private String codBarras;

    @NotNull(message = "O campo 'PREÇO DE ATACADO' precisa ser preenchido")
    private double precoAtacado;

    @NotNull(message = "O campo 'PREÇO DE VAREJO' precisa ser preenchido")
    private double precoVarejo;

    private double qteEstoque;
    private double qteMin;
    private double qteMax;
    private boolean status = true;

    public ProdutoDto() {
        super();
    }

    public ProdutoDto(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.codBarras = produto.getCodBarras();
        this.descricao = produto.getDescricao();
        this.precoAtacado = produto.getPrecoAtacado();
        this.precoVarejo = produto.getPrecoVarejo();
        this.qteEstoque = produto.getQteEstoque();
        this.qteMin = produto.getQteMin();
        this.qteMax = produto.getQteMax();
        this.status = produto.isStatus();
    }

    public ProdutoDto(Optional<Produto> produto) {
        if (produto.isPresent()) {
            this.idProduto = produto.get().getIdProduto();
            this.codBarras = produto.get().getCodBarras();
            this.descricao = produto.get().getDescricao();
            this.precoAtacado = produto.get().getPrecoAtacado();
            this.precoVarejo = produto.get().getPrecoVarejo();
            this.qteEstoque = produto.get().getQteEstoque();
            this.qteMin = produto.get().getQteMin();
            this.qteMax = produto.get().getQteMax();
            this.status = produto.get().isStatus();
        }
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

    public double getPrecoAtacado() {
        return precoAtacado;
    }

    public void setPrecoAtacado(double precoAtacado) {
        this.precoAtacado = precoAtacado;
    }

    public double getPrecoVarejo() {
        return precoVarejo;
    }

    public void setPrecoVarejo(double precoVarejo) {
        this.precoVarejo = precoVarejo;
    }

    public double getQteEstoque() {
        return qteEstoque;
    }

    public void setQteEstoque(double qteEstoque) {
        this.qteEstoque = qteEstoque;
    }

    public double getQteMin() {
        return qteMin;
    }

    public void setQteMin(double qteMin) {
        this.qteMin = qteMin;
    }

    public double getQteMax() {
        return qteMax;
    }

    public void setQteMax(double qteMax) {
        this.qteMax = qteMax;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
