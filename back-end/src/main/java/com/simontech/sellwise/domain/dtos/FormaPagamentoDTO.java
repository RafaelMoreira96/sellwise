package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormaPagamentoDTO {
    private Integer idFormaPagamento;
    private String descricao;


    public FormaPagamentoDTO(FormaPagamento formaPagamento) {
        this.idFormaPagamento = formaPagamento.getIdFormaPagamento();
        this.descricao = formaPagamento.getDescricao();
    }
}
