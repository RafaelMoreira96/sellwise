package com.simontech.sellwise.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.dtos.FormaPagamentoDto;
import com.simontech.sellwise.services.FormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/formaPagamento")
public class FormaPagamentoResource {
    @Autowired
    private FormaPagamentoService service;
    
    // Procura por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> findById(@PathVariable Integer id) {
        FormaPagamento obj = service.findById(id);
        return ResponseEntity.ok().body(new FormaPagamentoDto(obj));
    }

    // Lista todos
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDto>> findAll() {
        List<FormaPagamento> list = service.findAll();
        List<FormaPagamentoDto> listDTO = list.stream().map(obj -> new FormaPagamentoDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // Cadastrar
    @PostMapping
    public ResponseEntity<FormaPagamentoDto> create(@Valid @RequestBody FormaPagamentoDto formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.create(formaPagamentoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(formaPagamento.getIdFormaPagamento()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Atualizar
    @PutMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> update(@PathVariable Integer id, @RequestBody FormaPagamentoDto formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.update(id, formaPagamentoDTO);
        return ResponseEntity.ok().body(new FormaPagamentoDto(formaPagamento));
    }

    // Deletar 
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
