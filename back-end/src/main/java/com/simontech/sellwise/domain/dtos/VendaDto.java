package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.ItemVenda;
import com.simontech.sellwise.domain.Venda;
import com.simontech.sellwise.domain.enums.StatusVenda;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Schema(name = "Venda DTO", description = "Objeto de transferência de dados para Venda")
public class VendaDto {
    private Integer idVenda;
    private String numeroVenda;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVenda = LocalDate.now();

    private StatusVenda status;

    private Integer clienteId;
    private Integer funcionarioId;
    private List<ItemVenda> itensVenda; 
    private double valorVenda;
    private Integer formaPagamentoId;

    public VendaDto(Venda venda) {
        this.idVenda = venda.getIdVenda();
        this.numeroVenda = venda.getNumeroVenda();
        this.dataVenda = venda.getDataVenda();
        this.status = venda.getStatus();
        this.clienteId = venda.getCliente().getIdPessoa();
        this.funcionarioId = venda.getFuncionario().getIdPessoa();
        this.itensVenda = venda.getItensVenda();
        this.valorVenda = venda.getValorVenda();
        this.formaPagamentoId = venda.getFormaPagamento().getIdFormaPagamento();
    }

    public VendaDto(Optional<Venda> venda) {
        if (venda.isPresent()) {
            this.idVenda = venda.get().getIdVenda();
            this.numeroVenda = venda.get().getNumeroVenda();
            this.dataVenda = venda.get().getDataVenda();
            this.status = venda.get().getStatus();
            this.clienteId = venda.get().getCliente().getIdPessoa();
            this.funcionarioId = venda.get().getFuncionario().getIdPessoa();
            this.itensVenda = venda.get().getItensVenda();
            this.valorVenda = venda.get().getValorVenda();
            this.formaPagamentoId = venda.get().getFormaPagamento().getIdFormaPagamento();
        }
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public String getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(String numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda.stream()
                .map(item -> new ItemVenda(item.getIdItemVenda(), item.getIdProduto(), item.getDescricao(),
                        item.getCodBarras(), item.getPrecoVendido(), item.getDesconto(), item.getQuantidade()))
                .collect(Collectors.toList());
    }

    public void setItensVenda(List<ItemVenda> itens) {
        this.itensVenda = itens;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(Integer formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }
}