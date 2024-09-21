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

import com.simontech.sellwise.domain.Fornecedor;
import com.simontech.sellwise.domain.dtos.FornecedorDto;
import com.simontech.sellwise.services.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorResource {
    @Autowired
    private FornecedorService service;

    // Procura por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<FornecedorDto> findById(@PathVariable Integer id) {
        Fornecedor fornecedorObject = service.findById(id);
        return ResponseEntity.ok().body(new FornecedorDto(fornecedorObject));
    }

    // Lista todos
    @GetMapping
    public ResponseEntity<List<FornecedorDto>> findAll() {
        List<Fornecedor> listFornecedores = service.findAll();
        List<FornecedorDto> listFornecedoresDto = listFornecedores.stream().map(obj -> new FornecedorDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listFornecedoresDto);
    }

    // Cadastrar fornecedor
    @PostMapping
    public ResponseEntity<FornecedorDto> create(@Valid @RequestBody FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = service.create(fornecedorDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(fornecedor.getIdPessoa()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Atualizar fornecedor
    @PutMapping(value = "/{id}")
    public ResponseEntity<FornecedorDto> update(@PathVariable Integer id, @RequestBody FornecedorDto fornecedorDto) {
        Fornecedor fornecedor = service.update(id, fornecedorDto);
        return ResponseEntity.ok().body(new FornecedorDto(fornecedor));
    }

    // Deletar fornecedor
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FornecedorDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
