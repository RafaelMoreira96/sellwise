package com.simontech.sellwise.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "cliente")
public class Cliente extends Pessoa {
    protected String nome;
    @CPF protected String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    
    private boolean status = true;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Venda> vendasDoCliente = new ArrayList<>();
}