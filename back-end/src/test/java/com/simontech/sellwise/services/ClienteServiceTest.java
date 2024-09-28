package com.simontech.sellwise.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.dtos.ClienteDto;
import com.simontech.sellwise.repositories.ClienteRepository;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class ClienteServiceTest {

    private Validator validator;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private Endereco endereco;
    private Contato contato;
    private List<Contato> contatoList;
    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        cliente = new Cliente();
        cliente.setIdPessoa(1);
        cliente.setNome("João Silva");
        cliente.setCpf("467.200.620-42");
        cliente.setStatus(true);
        cliente.setDataCadastro(LocalDate.now());
        cliente.setDataNascimento(LocalDate.parse("1990-01-01", formatter));
        cliente.setStatus(true);

        endereco = new Endereco();
        endereco.setBairro("Jd. Progresso");
        endereco.setCep("79456-888");
        endereco.setCidade("Cidade Teste");
        endereco.setComplemento("Apto 201");
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("200");
        cliente.setEndereco(endereco);

        contato = new Contato();
        contato.setIdContato(1);
        contato.setNumero("987654321");
        contato.setTipo("Celular");
        contato.setPessoa(cliente);
        contatoList = new ArrayList<>();
        contatoList.add(contato);
        cliente.setContatos(contatoList);

        clienteDto = new ClienteDto();
        clienteDto.setContatos(contatoList);
        clienteDto.setDataCadastro(LocalDate.now());
        clienteDto.setDataNascimento(LocalDate.parse("1990-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        clienteDto.setNome("João Silva");
        clienteDto.setCpf("123.456.789-00");
        clienteDto.setStatus(true);
        clienteDto.setEndereco(endereco);
    }

    @Test
    void testFindByIdSuccess() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.findById(1);

        assertNotNull(foundCliente);
        assertEquals(cliente.getIdPessoa(), foundCliente.getIdPessoa());
        assertEquals(cliente.getNome(), foundCliente.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            clienteService.findById(1);
        });
        assertEquals("Cliente não encontrado! ID: 1", thrown.getMessage());
    }

    @Test
    void testFindAll() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testCreateCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.create(clienteDto);

        assertNotNull(savedCliente);
        assertEquals(cliente.getIdPessoa(), savedCliente.getIdPessoa());
    }

    @Test
    void testDeleteCliente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        clienteService.delete(1);

        assertFalse(cliente.isStatus());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testValidCpf() {
        clienteDto.setCpf("752.834.860-01");

        Set<ConstraintViolation<ClienteDto>> violations = validator.validate(clienteDto);

        assertTrue(violations.isEmpty(), "O CPF deve ser válido, mas falhou na validação.");
    }

    @Test
    void testInvalidCpf() {
        clienteDto.setCpf("123.456.789-00"); // Coloque um CPF inválido aqui

        Set<ConstraintViolation<ClienteDto>> violations = validator.validate(clienteDto);

        assertFalse(violations.isEmpty(), "O CPF deve ser inválido, mas passou na validação.");
    }
}
