package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.Fornecedor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Fornecedor DTO", description = "Objeto de transferência de dados para Fornecedor")
public class FornecedorDto {
    private Integer idFornecedor;

    @NotNull(message = "O campo 'RAZÃO SOCIAL' precisa ser preenchido")
    private String razaoSocial;

    @NotNull(message = "O campo 'NOME FANTASIA' precisa ser preenchido")
    private String nomeFantasia;

    @NotNull(message = "O campo 'INSCRIÇÃO ESTADUAL' precisa ser preenchido")
    private String inscricaoEstadual;

    @NotNull(message = "O campo 'CNPJ' precisa ser preenchido")
    @CNPJ
    private String cnpj;

    @NotNull(message = "O campo 'ENDERECO' precisa ser preenchido")
    private Endereco endereco;

    @NotNull(message = "O campo 'CONTATOS' precisa ser preenchido")
    private List<Contato> contatos = new ArrayList<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro = LocalDate.now();

    private boolean isActive = true;

    public FornecedorDto() {
        super();
    }

    public FornecedorDto(Fornecedor fornecedor) {
        this.idFornecedor = fornecedor.getIdPessoa();
        this.razaoSocial = fornecedor.getRazaoSocial();
        this.nomeFantasia = fornecedor.getNomeFantasia();
        this.inscricaoEstadual = fornecedor.getInscricaoEstadual();
        this.cnpj = fornecedor.getCnpj();
        this.endereco = fornecedor.getEndereco();
        this.contatos = fornecedor.getContatos();
        this.dataCadastro = fornecedor.getDataCadastro();
        this.isActive = fornecedor.isActive();
    }

    public FornecedorDto(Optional<Fornecedor> fornecedor) {
        if (fornecedor.isPresent()) {
            this.idFornecedor = fornecedor.get().getIdPessoa();
            this.razaoSocial = fornecedor.get().getRazaoSocial();
            this.nomeFantasia = fornecedor.get().getNomeFantasia();
            this.inscricaoEstadual = fornecedor.get().getInscricaoEstadual();
            this.cnpj = fornecedor.get().getCnpj();
            this.endereco = fornecedor.get().getEndereco();
            this.contatos = fornecedor.get().getContatos();
            this.dataCadastro = fornecedor.get().getDataCadastro();
            this.isActive = fornecedor.get().isActive();
        }
    }

    public Integer getIdFornecedor() {
        return this.idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
