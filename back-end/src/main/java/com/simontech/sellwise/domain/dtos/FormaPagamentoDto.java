package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.FormaPagamento;

public record FormaPagamentoDto(
    Integer idFormaPagamento,
    String descricao
) {
    public FormaPagamentoDto(FormaPagamento formaPagamento) {
        this(formaPagamento.getIdFormaPagamento(), formaPagamento.getDescricao());
    }
}
