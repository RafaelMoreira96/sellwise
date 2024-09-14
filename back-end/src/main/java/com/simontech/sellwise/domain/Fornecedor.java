package com.simontech.sellwise.domain;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "fornecedor")
public class Fornecedor extends Pessoa {
    private String razaoSocial;
    private String nomeFantasia;
    private String inscricaoEstadual;
    @CNPJ protected String cnpj;
    private boolean isActive = true;
}
