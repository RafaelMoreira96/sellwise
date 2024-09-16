package com.simontech.sellwise.domain.dtos;

import com.simontech.sellwise.domain.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FormaPagamentoDTO {
    private Integer idFormaPagamento;
    private String descricao;

    public static FormaPagamentoDTO fromEntity(FormaPagamento formaPagamento) {
        return new FormaPagamentoDTO(
            formaPagamento.getIdFormaPagamento(),
            formaPagamento.getDescricao()
        );
    }

    public FormaPagamento toEntity() {
        return new FormaPagamento(
            this.idFormaPagamento,
            this.descricao
        );
    }
}
