package com.simontech.sellwise.domain.enums;

public enum StatusVenda {
    ANDAMENTO(0, "ANDAMENTO"), CANCELADO(1, "CANCELADO"), FINALIZADO(2, "FINALIZADO");

    private Integer codigo;
    private String descricao;

    private StatusVenda(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusVenda toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (StatusVenda x : StatusVenda.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Status de venda inválido!");
    }
}
