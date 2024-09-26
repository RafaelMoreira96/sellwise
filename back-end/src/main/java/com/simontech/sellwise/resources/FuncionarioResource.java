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

import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.dtos.FuncionarioDto;
import com.simontech.sellwise.services.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Funcionário", description = "Operações CRUD relacionadas aos funcionários")
@RequestMapping("/funcionario")
public class FuncionarioResource {
    @Autowired
    private FuncionarioService service;

    @Operation(summary = "Busca funcionário por ID", description = "Retorna um funcionário a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDto> findById(@PathVariable Integer id) {
        Funcionario funcionarioObject = service.findById(id);
        return ResponseEntity.ok().body(new FuncionarioDto(funcionarioObject));
    }

    @Operation(summary = "Lista todos os funcionários", description = "Retorna uma lista com todos os funcionários cadastrados.")
    @GetMapping
    public ResponseEntity<List<FuncionarioDto>> findAll() {
        List<Funcionario> listFuncionarios = service.findAll();
        List<FuncionarioDto> list = listFuncionarios.stream().map(obj -> new FuncionarioDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Cadastra um novo funcionário", description = "Cadastra um novo funcionário.")
    @PostMapping
    public ResponseEntity<FuncionarioDto> create(@Valid @RequestBody FuncionarioDto funcionarioDto){
        Funcionario funcionario = service.create(funcionarioDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(funcionario.getIdPessoa()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualiza um funcionário", description = "Atualiza um funcionário a partir do ID.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDto> update(@PathVariable Integer id, @RequestBody FuncionarioDto funcionarioDto){
        Funcionario funcionario = service.update(id, funcionarioDto);
        return ResponseEntity.ok().body(new FuncionarioDto(funcionario));
    }

    @Operation(summary = "Deleta um funcionário", description = "Deleta um funcionário a partir do ID.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDto> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
