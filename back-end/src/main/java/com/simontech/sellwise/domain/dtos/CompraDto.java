package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.Compra;
import com.simontech.sellwise.domain.enums.StatusCompra;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Schema(name = "Compra DTO", description = "Objeto de transferência de dados para Compra")
public class CompraDto {
    private Integer idCompra;
    private String numeroCompra;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCompra;
    private Integer fornecedorId;
    private Integer funcionarioId;
    private List<ItemCompraDto> itensCompra;
    private StatusCompra status;
    private double valorTotal;

    public CompraDto(Compra compra) {
        this.idCompra = compra.getIdCompra();
        this.numeroCompra = compra.getNumeroCompra();
        this.dataCompra = compra.getDataCompra();
        this.status = compra.getStatus();
        this.fornecedorId = compra.getFornecedor().getIdPessoa();
        this.funcionarioId = compra.getFuncionario().getIdPessoa();
        this.itensCompra = compra.getItensCompra().stream()
            .map(ItemCompraDto::new)
            .collect(Collectors.toList());
        this.valorTotal = compra.getValorTotal();
    }

    public CompraDto(Optional<Compra> compra) {
        compra.ifPresent(c -> {
            this.idCompra = c.getIdCompra();
            this.numeroCompra = c.getNumeroCompra();
            this.dataCompra = c.getDataCompra();
            this.status = c.getStatus();
            this.fornecedorId = c.getFornecedor().getIdPessoa();
            this.funcionarioId = c.getFuncionario().getIdPessoa();
            this.itensCompra = c.getItensCompra().stream()
                .map(ItemCompraDto::new)
                .collect(Collectors.toList());
            this.valorTotal = c.getValorTotal();
        });
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public void setStatus(StatusCompra status) {
        this.status = status;
    }

    public Integer getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Integer fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public List<ItemCompraDto> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompraDto> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
