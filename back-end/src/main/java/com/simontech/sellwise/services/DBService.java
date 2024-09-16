package com.simontech.sellwise.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Cliente;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.FormaPagamento;
import com.simontech.sellwise.domain.Fornecedor;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.Produto;
import com.simontech.sellwise.domain.UserFuncionario;
import com.simontech.sellwise.domain.enums.NivelAutenticacao;
import com.simontech.sellwise.repositories.ClienteRepository;
import com.simontech.sellwise.repositories.FormaPagamentoRepository;
import com.simontech.sellwise.repositories.FornecedorRepository;
import com.simontech.sellwise.repositories.FuncionarioRepository;
import com.simontech.sellwise.repositories.ProdutoRepository;

@Service
public class DBService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void instanciaDB() {
        saveFormaPagamento();
        saveProduto();
        saveCliente();
        saveFornecedor();
        saveFuncionario();
    }

    public void saveFormaPagamento() {
        if (formaPagamentoRepository.count() > 0) {
            return;
        }

        FormaPagamento forma1 = new FormaPagamento(null, "Cartão de crédito");
        FormaPagamento forma2 = new FormaPagamento(null, "Cartão de débito");

        formaPagamentoRepository.saveAll(Arrays.asList(forma1, forma2));
    }

    public void saveProduto() {
        if (produtoRepository.count() > 0) {
            return;
        }

        Produto produto1 = new Produto(null, "Coca-cola", "0001", 5.0, 8.0, true, 4.0, 2.0, 100.0);
        Produto produto2 = new Produto(null, "Pão de almoço", "0002", 2.5, 4.0, true, 10.0, 3.0, 150.0);
        Produto produto3 = new Produto(null, "Arroz", "0003", 3.0, 5.0, true, 15.0, 2.0, 200.0);

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
    }

    public void saveCliente() {
        if (clienteRepository.count() > 0) {
            return;
        }

        Cliente cliente1 = new Cliente();
        cliente1.setIdPessoa(4L);
        cliente1.setNome("Cliente Teste");
        cliente1.setCpf("075.971.350-25");
        cliente1.setDataNascimento(LocalDate.parse("01/01/1990", formatter));
        cliente1.setStatus(true);
        cliente1.setEndereco(new Endereco(null, "79456-888", "Rua Teste", "123", "complemento de teste", "cidade teste",
                "32145678900", "SP"));
        cliente1.getContatos().add(new Contato(null, "1234567890", "11", cliente1));
        cliente1.getContatos().add(new Contato(null, "9876543210", "11", cliente1));
        cliente1.getContatos().add(new Contato(null, "5555555555", "11", cliente1));

        Cliente cliente2 = new Cliente();
        cliente2.setIdPessoa(5L);
        cliente2.setNome("Cliente Teste 2");
        cliente2.setCpf("075.971.350-25");
        cliente2.setDataNascimento(LocalDate.parse("01/01/1990", formatter));
        cliente2.setStatus(true);
        cliente2.setEndereco(new Endereco(null, "98765-444", "Rua Teste 2", "432", "complemento de teste 2",
                "cidade teste 2", "32145678900", "SP"));
        cliente2.getContatos().add(new Contato(null, "1234567890", "11", cliente2));
        cliente2.getContatos().add(new Contato(null, "9876543210", "11", cliente2));
        cliente2.getContatos().add(new Contato(null, "5555555555", "11", cliente2));
        cliente2.getContatos().add(new Contato(null, "6666666666", "11", cliente2));

        Cliente cliente3 = new Cliente();
        cliente3.setIdPessoa(6L);
        cliente3.setNome("Cliente Teste 3");
        cliente3.setCpf("075.971.350-25");
        cliente3.setDataNascimento(LocalDate.parse("01/01/1990", formatter));
        cliente3.setStatus(true);
        cliente3.setEndereco(new Endereco(null, "65432-111", "Rua Teste 3", "789", "complemento de teste 3",
                "cidade teste 3", "32145678900", "SP"));
        cliente3.getContatos().add(new Contato(null, "1234567890", "11", cliente3));
        cliente3.getContatos().add(new Contato(null, "9876543210", "11", cliente3));
        cliente3.getContatos().add(new Contato(null, "5555555555", "11", cliente3));
        cliente3.getContatos().add(new Contato(null, "4444444444", "11", cliente3));
        cliente3.getContatos().add(new Contato(null, "3333333333", "11", cliente3));
        cliente3.getContatos().add(new Contato(null, "2222222222", "11", cliente3));

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
    }

    public void saveFornecedor() {
        if (fornecedorRepository.count() > 0) {
            return;
        }

        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setIdPessoa(7L);
        fornecedor1.setNomeFantasia("Fornecedor Teste");
        fornecedor1.setInscricaoEstadual("Teste");
        fornecedor1.setDataCadastro(LocalDate.now());
        fornecedor1.setCnpj("02.315.215/0001-27");
        fornecedor1.setActive(true);
        fornecedor1.setRazaoSocial("Teste");
        fornecedor1
                .setEndereco(new Endereco(null, "12345-678", "Rua Teste", "123", "complemento de teste", "cidade teste",
                        "32145678900", "SP"));
        fornecedor1.getContatos().add(new Contato(null, "1234567890", "11", fornecedor1));
        fornecedor1.getContatos().add(new Contato(null, "9876543210", "11", fornecedor1));
        fornecedor1.getContatos().add(new Contato(null, "5555555555", "11", fornecedor1));

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setIdPessoa(8L);
        fornecedor2.setNomeFantasia("Fornecedor Teste 2");
        fornecedor2.setInscricaoEstadual("Teste 2");
        fornecedor2.setDataCadastro(LocalDate.now());
        fornecedor2.setCnpj("80.984.869/0001-57");
        fornecedor2.setActive(true);
        fornecedor2.setRazaoSocial("Teste 2");
        fornecedor2
                .setEndereco(new Endereco(null, "65432-111", "Rua Teste 2", "432", "complemento de teste 2",
                        "cidade teste 2", "32145678900", "SP"));
        fornecedor2.getContatos().add(new Contato(null, "1234567890", "11", fornecedor2));
        fornecedor2.getContatos().add(new Contato(null, "9876543210", "11", fornecedor2));
        fornecedor2.getContatos().add(new Contato(null, "5555555555", "11", fornecedor2));
        fornecedor2.getContatos().add(new Contato(null, "6666666666", "11", fornecedor2));
        fornecedorRepository.saveAll(Arrays.asList(fornecedor1, fornecedor2));
    }

    public void saveFuncionario() {
        if (funcionarioRepository.count() > 0) {
            return;
        }

        Funcionario funcionario1 = new Funcionario();
        funcionario1.setIdPessoa(9L);
        funcionario1.setNome("Funcionario Teste");
        funcionario1.setCpf("594.946.940-23");
        funcionario1.setDataAdmissao(LocalDate.now());
        funcionario1.setDataCadastro(LocalDate.now());
        funcionario1.setDataDemissao(null);
        funcionario1
                .setEndereco(new Endereco(null, "12345-678", "Rua Teste", "123", "complemento de teste", "cidade teste",
                        "32145678900", "SP"));
        funcionario1.getContatos().add(new Contato(null, "1234567890", "11", funcionario1));
        funcionario1.getContatos().add(new Contato(null, "9876543210", "11", funcionario1));
        funcionario1.getContatos().add(new Contato(null, "5555555555", "11", funcionario1));
        funcionario1.getContatos().add(new Contato(null, "6666666666", "11", funcionario1));
        funcionario1.setNivelAutenticacao(NivelAutenticacao.PADRAO);
        funcionario1.setUserFuncionario(new UserFuncionario(null, "teste.teste", "123456", funcionario1));

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setIdPessoa(10L);
        funcionario2.setNome("Funcionario Teste 2");
        funcionario2.setCpf("821.459.970-91");
        funcionario2.setDataAdmissao(LocalDate.now());
        funcionario2.setDataCadastro(LocalDate.now());
        funcionario2.setDataDemissao(null);
        funcionario2
                .setEndereco(new Endereco(null, "65432-111", "Rua Teste 2", "432", "complemento de teste 2",
                        "cidade teste 2", "32145678900", "SP"));
        funcionario2.getContatos().add(new Contato(null, "1234567890", "11", funcionario2));
        funcionario2.getContatos().add(new Contato(null, "9876543210", "11", funcionario2));
        funcionario2.getContatos().add(new Contato(null, "5555555555", "11", funcionario2));
        funcionario2.getContatos().add(new Contato(null, "6666666666", "11", funcionario2));
        funcionario2.setNivelAutenticacao(NivelAutenticacao.ADMIN);
        funcionario2.setUserFuncionario(new UserFuncionario(null, "teste.teste2", "123456", funcionario2));
        
        funcionarioRepository.saveAll(Arrays.asList(funcionario1, funcionario2));
    }

}
