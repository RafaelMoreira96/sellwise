package com.simontech.sellwise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.dtos.FormaPagamentoDto;
import com.simontech.sellwise.repositories.FormaPagamentoRepository;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class FormaPagamentoService {
    @Autowired
    private FormaPagamentoRepository repository;

     // Busca por ID
    public FormaPagamento findById(Integer id) {
        Optional<FormaPagamento> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException("FormaPagamento não encontrado! ID: " + id));
    }
     // Lista todos
    public List<FormaPagamento> findAll() {
        return repository.findAll();
    }

    // Cadastrar
    public FormaPagamento create(@Valid FormaPagamentoDto formaPagamentoDto) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setIdFormaPagamento(null);
        formaPagamento.setDescricao(formaPagamentoDto.descricao());
        return repository.save(formaPagamento);
    }

    // Atualizar
    public FormaPagamento update(Integer id, @Valid FormaPagamentoDto formaPagamentoDTO) {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setIdFormaPagamento(id);
        formaPagamento.setDescricao(formaPagamentoDTO.descricao());
        return repository.save(formaPagamento);
    }

    /*
     * "Remover": aqui não pode ser deletado, ele deve ser
     * "desativado", através do atributo
     */
    public void delete(Integer id) {
        FormaPagamento formaPagamento = findById(id);
        repository.delete(formaPagamento);
    }
}
