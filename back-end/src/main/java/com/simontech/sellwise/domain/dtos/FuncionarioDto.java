package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.UserFuncionario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Funcionário DTO", description = "Objeto de transferência de dados para Funcionário")
public class FuncionarioDto {
    private Integer idFuncionario;

    @NotNull(message = "O campo 'NOME' precisa ser preenchido")
    private String nome;

    @NotNull(message = "O campo 'CPF' precisa ser preenchido")
    private String cpf;

    @NotNull(message = "Os campos de 'USUÁRIO' devem ser preenchido")
    private UserFuncionario userFuncionario;

    @NotNull(message = "O campo 'DATA DE NASCIMENTO' precisa ser preenchido")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "O campo 'ENDERECO' precisa ser preenchido")
    private Endereco endereco;

    @NotNull(message = "O campo 'CONTATOS' precisa ser preenchido")
    private List<Contato> contatos = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    private boolean status = true;

    public FuncionarioDto() {
        super();
    }

    public FuncionarioDto(Funcionario funcionario) {
        this.idFuncionario = funcionario.getIdPessoa();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.endereco = funcionario.getEndereco();
        this.contatos = funcionario.getContatos();
        this.dataCadastro = funcionario.getDataCadastro();
        this.dataNascimento = funcionario.getDataNascimento();
        this.userFuncionario = funcionario.getUserFuncionario();
        this.status = funcionario.isStatus();
    }

    public FuncionarioDto(Optional<Funcionario> funcionario) {
        if (funcionario.isPresent()) {
            this.idFuncionario = funcionario.get().getIdPessoa();
            this.nome = funcionario.get().getNome();
            this.cpf = funcionario.get().getCpf();
            this.endereco = funcionario.get().getEndereco();
            this.contatos = funcionario.get().getContatos();
            this.dataCadastro = funcionario.get().getDataCadastro();
            this.dataNascimento = funcionario.get().getDataNascimento();
            this.userFuncionario = funcionario.get().getUserFuncionario();
            this.status = funcionario.get().isStatus();
        }
    }

    public Integer getIdFuncionario() {
        return this.idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Contato> getContatos() {
        return this.contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public UserFuncionario getUserFuncionario() {
        return this.userFuncionario;
    }

    public void setUserFuncionario(UserFuncionario userFuncionario) {
        this.userFuncionario = userFuncionario;
    }
}
