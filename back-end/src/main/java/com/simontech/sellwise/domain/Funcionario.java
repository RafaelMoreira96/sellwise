package com.simontech.sellwise.domain;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.enums.NivelAutenticacao;

import io.swagger.v3.oas.annotations.Hidden;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Hidden
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
    private LocalDate dataNascimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    private boolean status = true;

    @Enumerated(EnumType.STRING)
    private NivelAutenticacao nivelAutenticacao;
}
