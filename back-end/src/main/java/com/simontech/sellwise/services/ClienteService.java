package com.simontech.sellwise.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.dtos.ClienteDto;
import com.simontech.sellwise.repositories.ClienteRepository;
import com.simontech.sellwise.repositories.ContatoRepository;
import com.simontech.sellwise.repositories.EnderecoRepository;
import com.simontech.sellwise.services.exceptions.DataIntegrityViolationException;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {
     @Autowired
    private ClienteRepository repository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ContatoRepository contatoRepository;

    // Busca por ID
    public Cliente findById(Integer id) {
        Optional<Cliente> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! ID: " + id));
    }

    // Busca por CPF
    public Cliente findByCpf(String cpf) {
        Optional<Cliente> o = repository.findByCpf(cpf);
        return o.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));
    }

    // Lista todos
    public List<Cliente> findAll() {
        List<Cliente> listClientesDB = repository.findAll();
        List<Cliente> listActiveClientes = new ArrayList<>();
        for (Cliente cliente : listClientesDB) {
            if (cliente.isStatus() == true) {
                listActiveClientes.add(cliente);
            }
        }
        return listActiveClientes;
    }

    public Cliente create(@Valid ClienteDto clienteDto) {
        validaCpf(clienteDto);
    
        // Preparando objeto Endereço e persistindo
        Endereco e = new Endereco();
        e.setCep(clienteDto.getEndereco().getCep());
        e.setNumero(clienteDto.getEndereco().getNumero());
        e.setComplemento(clienteDto.getEndereco().getComplemento());
        e.setBairro(clienteDto.getEndereco().getBairro());
        e.setCidade(clienteDto.getEndereco().getCidade());
        e.setEstado(clienteDto.getEndereco().getEstado());
        e.setLogradouro(clienteDto.getEndereco().getLogradouro());
        
        enderecoRepository.save(e);
    
        // Preparando objeto Cliente para persistência
        Cliente cliente = new Cliente();
        cliente.setIdPessoa(null);
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEndereco(e);
        cliente.setNome(clienteDto.getNome());
        cliente.setStatus(clienteDto.getStatus());
    
        // Associando contatos ao cliente
        List<Contato> contatos = new ArrayList<>();
        for (Contato contato : clienteDto.getContatos()) {
            contato.setIdContato(null); // Garante que o ID seja gerado pelo banco
            contato.setPessoa(cliente); // Associa o contato ao cliente
            contatos.add(contato);
        }
        cliente.setContatos(contatos);
    
        // Salvando cliente (isso irá salvar também os contatos devido ao CascadeType.ALL)
        return repository.save(cliente);
    }
    

    // Atualizar cliente
    public Cliente update(Integer idPessoa, @Valid ClienteDto clienteDto) {
        clienteDto.setIdCliente(idPessoa);
        Cliente clienteDB = findById(idPessoa);
        if (clienteDto.getCpf().equals(clienteDB.getCpf())) {
            clienteDto.getEndereco().setIdEndereco(clienteDB.getEndereco().getIdEndereco());
        } else {
            validaCpf(clienteDto);
            clienteDto.getEndereco().setIdEndereco(clienteDB.getEndereco().getIdEndereco());
        }
        // Preparando objeto Endereço e persistindo
        Endereco endereco = new Endereco();

        endereco.setIdEndereco(clienteDto.getEndereco().getIdEndereco());
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setNumero(clienteDto.getEndereco().getNumero());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setEstado(clienteDto.getEndereco().getEstado());
        endereco.setLogradouro(clienteDto.getEndereco().getLogradouro());

        enderecoRepository.save(endereco);

        // Preparando objeto Contato e persistindo
        Cliente temporarioCliente = findById(clienteDto.getIdCliente());
        List<Contato> contatos = new ArrayList<>();
        for (Contato contato : clienteDto.getContatos()) {
            contato.setIdContato(null);
            contatos.add(contato);
        }
        clienteDto.setContatos(contatos);
        for (int i = 0; i < temporarioCliente.getContatos().size(); i++) {
            Contato contato = temporarioCliente.getContatos().get(i);
            contatos.get(i).setIdContato(contato.getIdContato());
        }
        contatoRepository.saveAll(contatos);

        // Preparando objeto Cliente para persistencia
        Cliente cliente = new Cliente();
        cliente.setIdPessoa(idPessoa);
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEndereco(endereco);
        cliente.setContatos(contatos);
        cliente.setNome(clienteDto.getNome());
        return repository.save(cliente);
    }

    /*
     * "Remover" cliente: aqui não pode ser deletado um cliente, ele deve ser
     * "desativado", através do atributo
     */
    public void delete(Integer id) {
        Cliente cliente = findById(id);
        cliente.setStatus(false);
        repository.save(cliente);
        //repository.delete(cliente);
    }

    public void validaCpf(ClienteDto clienteDto) {
        // Verificação de todos os objetos que contém o mesmo CPF (que é pra encontrar
        // apenas um)
        Optional<Cliente> obj = repository.findByCpf(clienteDto.getCpf());
        if (obj.isPresent() && obj.get().getCpf() != clienteDto.getCpf()) {
            throw new DataIntegrityViolationException("CPF já cadastrado!");
        }
    }
}
