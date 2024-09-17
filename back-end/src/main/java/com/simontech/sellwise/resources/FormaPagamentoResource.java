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
import com.simontech.sellwise.domain.dtos.FormaPagamentoDTO;
import com.simontech.sellwise.services.FormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/formaPagamento")
public class FormaPagamentoResource {
    @Autowired
    private FormaPagamentoService service;
    
    // Procura por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> findById(@PathVariable Integer id) {
        FormaPagamento obj = service.findById(id);
        return ResponseEntity.ok().body(new FormaPagamentoDTO(obj));
    }

    // Lista todos
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> findAll() {
        List<FormaPagamento> list = service.findAll();
        List<FormaPagamentoDTO> listDTO = list.stream().map(obj -> new FormaPagamentoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    // Cadastrar
    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> create(@Valid @RequestBody FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.create(formaPagamentoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(formaPagamento.getIdFormaPagamento()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Atualizar
    @PutMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> update(@PathVariable Integer id, @RequestBody FormaPagamentoDTO formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.update(id, formaPagamentoDTO);
        return ResponseEntity.ok().body(new FormaPagamentoDTO(formaPagamento));
    }

    // Deletar 
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
