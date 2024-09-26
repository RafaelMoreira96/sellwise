package com.simontech.sellwise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "password" })
@Entity
@Table(name = "user_funcionario")
public class UserFuncionario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUserFuncionario;
    private String username;
    private String password;

    @OneToOne(mappedBy = "userFuncionario")
    @JsonIgnore
    private Funcionario funcionario;
}
