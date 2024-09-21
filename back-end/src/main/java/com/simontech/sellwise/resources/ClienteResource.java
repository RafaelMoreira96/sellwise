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

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {
    @Autowired
    private ClienteService service;

    // Procura por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
        Cliente clienteObject = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDto(clienteObject));
    }

    // Lista todos
    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<Cliente> listClientes = service.findAll();
        List<ClienteDto> listClientesDto = listClientes.stream().map(obj -> new ClienteDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listClientesDto);
    }

    // Cadastrar cliente
    @PostMapping
    public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.create(clienteDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cliente.getIdPessoa()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Atualizar cliente
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto) {
        Cliente cliente = service.update(id, clienteDto);
        return ResponseEntity.ok().body(new ClienteDto(cliente));
    }

    // Deletar cliente
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
