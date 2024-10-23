package com.simontech.sellwise.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.dtos.ClienteDto;
import com.simontech.sellwise.repositories.ClienteRepository;
import com.simontech.sellwise.services.exceptions.DataIntegrityViolationException;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    // Busca por ID
    public Cliente findById(Integer id) {
        Optional<Cliente> optionalCliente = repository.findById(id);
        return optionalCliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! ID: " + id));
    }

    // Busca por CPF
    public Cliente findByCpf(String cpf) {
        Optional<Cliente> optionalCliente = repository.findByCpf(cpf);
        return optionalCliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado."));
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
        Endereco endereco = new Endereco();
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setNumero(clienteDto.getEndereco().getNumero());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setEstado(clienteDto.getEndereco().getEstado());
        endereco.setLogradouro(clienteDto.getEndereco().getLogradouro());

        // Preparando objeto Cliente para persistência
        Cliente cliente = new Cliente();
        cliente.setIdPessoa(null);
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEndereco(endereco);
        cliente.setNome(clienteDto.getNome());
        cliente.setDataNascimento(clienteDto.getDataNascimento());
        cliente.setStatus(clienteDto.getStatus());

        // Associando contatos ao cliente
        List<Contato> contatos = new ArrayList<>();
        for (Contato contato : clienteDto.getContatos()) {
            contato.setIdContato(null);
            contato.setPessoa(cliente);
            contatos.add(contato);
        }
        cliente.setContatos(contatos);

        return repository.save(cliente);
    }

    // Atualizar cliente
    public Cliente update(Integer idPessoa, @Valid ClienteDto clienteDto) {
        // Buscar o cliente existente no banco de dados
        Cliente clienteDB = findById(idPessoa);

        // Verificar se o CPF foi alterado
        if (!clienteDto.getCpf().equals(clienteDB.getCpf())) {
            validaCpf(clienteDto);
        }

        // Atualizando primeiros atributos de cliente
        clienteDB.setNome(clienteDto.getNome());
        clienteDB.setDataNascimento(clienteDto.getDataNascimento());
        clienteDB.setStatus(clienteDto.getStatus());

        List<Contato> contatos = clienteDB.getContatos();
        for (Contato contato : clienteDto.getContatos()) {
            Optional<Contato> contatoExistente = clienteDB.getContatos().stream()
                    .filter(c -> c.getIdContato().equals(contato.getIdContato()))
                    .findFirst();

            if (contatoExistente.isPresent()) {
                contatoExistente.get().setNumero(contato.getNumero());
                contatoExistente.get().setTipo(contato.getTipo());
            } else {
                contato.setIdContato(null);
                contato.setPessoa(clienteDB);
                contatos.add(contato);
            }
        }

        // Atualizar o endereço existente
        Endereco endereco = clienteDB.getEndereco();
        endereco.setCep(clienteDto.getEndereco().getCep());
        endereco.setNumero(clienteDto.getEndereco().getNumero());
        endereco.setComplemento(clienteDto.getEndereco().getComplemento());
        endereco.setBairro(clienteDto.getEndereco().getBairro());
        endereco.setCidade(clienteDto.getEndereco().getCidade());
        endereco.setEstado(clienteDto.getEndereco().getEstado());
        endereco.setLogradouro(clienteDto.getEndereco().getLogradouro());
        clienteDB.setEndereco(endereco);

        // Salvar as alterações no banco de dados
        return repository.save(clienteDB);
    }

    /*
     * "Remover" cliente: aqui não pode ser deletado um cliente, ele deve ser
     * "desativado", através do atributo
     */
    public void delete(Integer id) {
        Cliente cliente = findById(id);
        cliente.setStatus(false);
        repository.save(cliente);
        // repository.delete(cliente);
    }

    public void validaCpf(ClienteDto clienteDto) {
        // Verificação de todos os objetos que contém o mesmo CPF (que é pra encontrar
        // apenas um)
        Optional<Cliente> obj = repository.findByCpf(clienteDto.getCpf());
        if (obj.isPresent() && obj.get().getCpf() != clienteDto.getCpf()) {
            throw new DataIntegrityViolationException("CPF já cadastrado!");
        }
    }

    public Map<String, Object> getFiveLastClientes() {
        List<Cliente> listClientes = repository.findLastCliente();
        List<ClienteDto> listClientesDto = new ArrayList<>();
        for (Cliente cliente : listClientes) {
            if (cliente.isStatus() == true) {
                listClientesDto.add(new ClienteDto(cliente));
            }
        }
        
        Collections.sort(listClientesDto, Comparator.comparing(ClienteDto::getDataCadastro).reversed());
        if (listClientesDto.size() < 5) {
            return Map.of("clientes", listClientesDto.subList(0, listClientesDto.size()));
        }
        return Map.of("clientes", listClientesDto.subList(0, 5));
    }
}
