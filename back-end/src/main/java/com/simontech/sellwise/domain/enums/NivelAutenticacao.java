package com.simontech.sellwise.domain.enums;

public enum NivelAutenticacao {
    ADMIN(0, "ROLE_ADMIN"), PADRAO(1, "ROLE_PADRAO");

    private Integer codigo;
    private String descricao;

    private NivelAutenticacao(Integer codigo, String descricao) {
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

    public static NivelAutenticacao toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (NivelAutenticacao x : NivelAutenticacao.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Permissão inválida!");
    }
}
