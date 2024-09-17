package com.simontech.sellwise.domain.enums;

public enum StatusCompra {
    ANDAMENTO(0, "ANDAMENTO"), DEVOLUCAO(1, "DEVOLUCAO"), FINALIZADO(2, "FINALIZADO");

    private Integer codigo;
    private String descricao;

    private StatusCompra(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static StatusCompra toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (StatusCompra x : StatusCompra.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Status da compra inválido!");
    }
}
