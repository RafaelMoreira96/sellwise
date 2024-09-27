package com.simontech.sellwise.domain.dtos;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.ItemVenda;
import com.simontech.sellwise.domain.Venda;
import com.simontech.sellwise.domain.enums.StatusVenda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Schema(name = "Venda DTO", description = "Objeto de transferência de dados para Venda")
public class VendaDto {
    private Integer idVenda;
    private Integer numeroVenda;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVenda = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @OneToMany
    private List<ItemVenda> itens = new ArrayList<>();

    private double valorVenda;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    private StatusVenda status = StatusVenda.FINALIZADO;

    public VendaDto() {
        super();
    }

    public VendaDto(Venda venda) {
        this.idVenda = venda.getIdVenda();
        this.numeroVenda = venda.getNumeroVenda();
        this.dataVenda = venda.getDataVenda();
        this.cliente = venda.getCliente();
        this.funcionario = venda.getFuncionario();
        this.itens = venda.getItens();
        this.valorVenda = venda.getValorVenda();
        this.formaPagamento = venda.getFormaPagamento();
        this.status = venda.getStatus();
    }

    public VendaDto(Optional<Venda> venda) {
        if (venda.isPresent()) {
            this.idVenda = venda.get().getIdVenda();
            this.numeroVenda = venda.get().getNumeroVenda();
            this.dataVenda = venda.get().getDataVenda();
            this.cliente = venda.get().getCliente();
            this.funcionario = venda.get().getFuncionario();
            this.itens = venda.get().getItens();
            this.valorVenda = venda.get().getValorVenda();
            this.formaPagamento = venda.get().getFormaPagamento();
            this.status = venda.get().getStatus();
        }
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getNumeroVenda() {
        return numeroVenda;
    }

    public void setNumeroVenda(Integer numeroVenda) {
        this.numeroVenda = numeroVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

}
