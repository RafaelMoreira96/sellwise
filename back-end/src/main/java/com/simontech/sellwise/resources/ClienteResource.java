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

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.dtos.ClienteDto;
import com.simontech.sellwise.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name="Cliente", description = "Operações CRUD relacionadas ao clientes")
@RequestMapping(value = "/cliente")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    @Operation(summary = "Busca cliente por ID", description = "Retorna um cliente a partir do ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
        Cliente clienteObject = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDto(clienteObject));
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<Cliente> listClientes = service.findAll();
        List<ClienteDto> listClientesDto = listClientes.stream().map(obj -> new ClienteDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listClientesDto);
    }

    @Operation(summary = "Cadastra um novo cliente", description = "Cadastra um novo cliente.")
    @PostMapping
    public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.create(clienteDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cliente.getIdPessoa()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualiza um cliente", description = "Atualiza um cliente a partir do ID.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.update(id, clienteDto);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    @Operation(summary = "Deleta um cliente", description = "Deleta um cliente a partir do ID.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
