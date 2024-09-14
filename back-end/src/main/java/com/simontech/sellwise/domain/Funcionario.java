package com.simontech.sellwise.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "funcionario")
public class Funcionario extends Pessoa {
    private String nome;
    @CPF private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_funcionario_id", referencedColumnName = "idUserFuncionario")
    private UserFuncionario userFuncionario;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDemissao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao = LocalDate.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "NivelAutenticacao")
    private Set<Integer> nivelAutenticacao = new HashSet<>();
}
