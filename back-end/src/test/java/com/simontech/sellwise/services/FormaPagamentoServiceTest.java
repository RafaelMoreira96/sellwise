package com.simontech.sellwise.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.dtos.FormaPagamentoDto;
import com.simontech.sellwise.repositories.FormaPagamentoRepository;

public class FormaPagamentoServiceTest {

    @Mock
    private FormaPagamentoRepository repository;

    @InjectMocks
    private FormaPagamentoService service;

    private FormaPagamento formaPagamento;
    private FormaPagamentoDto formaPagamentoDto;

    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks

        formaPagamento = new FormaPagamento();
        formaPagamento.setIdFormaPagamento(1);
        formaPagamento.setDescricao("Cartão de Crédito");

        formaPagamentoDto = new FormaPagamentoDto(formaPagamento.getIdFormaPagamento(), formaPagamento.getDescricao());
    }

    @Test
    void testFindById() {
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setIdFormaPagamento(1);
        formaPagamento.setDescricao("Cartão de Crédito");

        when(repository.findById(1)).thenReturn(Optional.of(formaPagamento));

        FormaPagamento result = service.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdFormaPagamento());
        assertEquals("Cartão de Crédito", result.getDescricao());
    }

}
