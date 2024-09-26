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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Forma de Pagamento", description = "Operações CRUD relacionadas às formas de pagamento")
@RequestMapping(value = "/formaPagamento")
public class FormaPagamentoResource {
    @Autowired
    private FormaPagamentoService service;
    
    @Operation(summary = "Busca forma de pagamento por ID", description = "Retorna uma forma de pagamento a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> findById(@PathVariable Integer id) {
        FormaPagamento obj = service.findById(id);
        return ResponseEntity.ok().body(new FormaPagamentoDto(obj));
    }

    @Operation(summary = "Lista todas as formas de pagamento", description = "Retorna uma lista com todas as formas de pagamento cadastradas.")
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDto>> findAll() {
        List<FormaPagamento> list = service.findAll();
        List<FormaPagamentoDto> listDTO = list.stream().map(obj -> new FormaPagamentoDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @Operation(summary = "Cadastra uma nova forma de pagamento", description = "Cadastra uma nova forma de pagamento.")
    @PostMapping
    public ResponseEntity<FormaPagamentoDto> create(@Valid @RequestBody FormaPagamentoDto formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.create(formaPagamentoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(formaPagamento.getIdFormaPagamento()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualiza uma forma de pagamento", description = "Atualiza uma forma de pagamento a partir do ID.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> update(@PathVariable Integer id, @RequestBody FormaPagamentoDto formaPagamentoDTO) {
        FormaPagamento formaPagamento = service.update(id, formaPagamentoDTO);
        return ResponseEntity.ok().body(new FormaPagamentoDto(formaPagamento));
    }

    @Operation(summary = "Deleta uma forma de pagamento", description = "Deleta uma forma de pagamento a partir do ID.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FormaPagamentoDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
