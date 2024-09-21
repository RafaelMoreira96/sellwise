package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;

import jakarta.validation.constraints.NotNull;

public class ClienteDto {
    
    private Integer idCliente;

    @NotNull(message = "O campo 'NOME' precisa ser preenchido")
    private String nome;

    @NotNull(message = "O campo 'CPF' precisa ser preenchido")
    private String cpf;

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

    public ClienteDto() {
        super();
    }

    public ClienteDto(Cliente cliente) {
        this.idCliente = cliente.getIdPessoa();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.contatos = cliente.getContatos();
        this.dataCadastro = cliente.getDataCadastro();
        this.dataNascimento = cliente.getDataNascimento();
        this.status = cliente.isStatus();
    }

    public ClienteDto(Optional<Cliente> cliente) {
        if (cliente.isPresent()) {
            this.idCliente = cliente.get().getIdPessoa();
            this.nome = cliente.get().getNome();
            this.cpf = cliente.get().getCpf();
            this.endereco = cliente.get().getEndereco();
            this.contatos = cliente.get().getContatos();
            this.dataCadastro = cliente.get().getDataCadastro();
            this.dataNascimento = cliente.get().getDataNascimento();
            this.status = cliente.get().isStatus();
        }
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

}