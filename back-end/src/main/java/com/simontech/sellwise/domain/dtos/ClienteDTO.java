package com.simontech.sellwise.domain.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.simontech.sellwise.domain.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClienteDTO {
    
    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    private boolean status;

    public static ClienteDTO fromEntity(com.simontech.sellwise.domain.Cliente cliente) {
        return new ClienteDTO(
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getDataNascimento(),
            cliente.isStatus()
        );
    }
    
    public Cliente toEntity() {
        return new Cliente(
            this.nome,
            this.cpf,
            this.dataNascimento,
            this.status
        );
    }
}
