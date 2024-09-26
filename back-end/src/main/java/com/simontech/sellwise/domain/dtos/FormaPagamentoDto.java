package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.FormaPagamento;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Forma de Pagamento DTO", description = "Objeto de transferência de dados para Forma de Pagamento")
public record FormaPagamentoDto(
    Integer idFormaPagamento,
    String descricao
) {
    public FormaPagamentoDto(FormaPagamento formaPagamento) {
        this(formaPagamento.getIdFormaPagamento(), formaPagamento.getDescricao());
    }
}
