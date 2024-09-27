package com.simontech.sellwise.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;

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

import com.simontech.sellwise.domain.Produto;
import com.simontech.sellwise.domain.dtos.ProdutoDto;
import com.simontech.sellwise.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Produto", description = "Operações CRUD relacionadas aos produtos")
@RequestMapping("/produto")
public class ProdutoResource {
    @Autowired
    private ProdutoService service;

    @Operation(summary = "Busca produto por ID", description = "Retorna um produto a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Integer id) {
        Produto produtoObject = service.findById(id);
        return ResponseEntity.ok().body(new ProdutoDto(produtoObject));
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados.")
    @GetMapping
    public ResponseEntity<List<ProdutoDto>> findAll() {
        List<Produto> listProdutos = service.findAll();
        List<ProdutoDto> list = listProdutos.stream().map(obj -> new ProdutoDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Cadastra um novo produto", description = "Cadastra um novo produto.")
    @PostMapping
    public ResponseEntity<ProdutoDto> create(@RequestBody ProdutoDto produtoDto) {
        Produto produto = service.create(produtoDto);
        URI uri = URI.create("/produto/" + produto.getIdProduto());
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }

    @Operation(summary = "Atualiza um funcionário", description = "Atualiza um funcionário a partir do ID.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDto> update(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto) {
        Produto produto = service.update(id, produtoDto);
        return ResponseEntity.ok().body(new ProdutoDto(produto));
    }

    @Operation(summary = "Deleta um funcionario", description = "Deleta um funcionario a partir do ID.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
