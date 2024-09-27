package com.simontech.sellwise.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.Funcionario;
import com.simontech.sellwise.domain.UserFuncionario;
import com.simontech.sellwise.domain.dtos.FuncionarioDto;
import com.simontech.sellwise.domain.enums.NivelAutenticacao;
import com.simontech.sellwise.repositories.FuncionarioRepository;
import com.simontech.sellwise.repositories.UserFuncionarioRepository;
import com.simontech.sellwise.services.exceptions.DataIntegrityViolationException;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private UserFuncionarioRepository userFuncionarioRepository;

    // Busca por ID
    public Funcionario findById(Integer id) {
        Optional<Funcionario> optionalFuncionario = repository.findById(id);
        return optionalFuncionario
                .orElseThrow(() -> new ObjectNotFoundException("Funcionário não encontrado! ID: " + id));
    }

    // Busca por CPF
    public Funcionario findByCpf(String cpf) {
        Optional<Funcionario> optionalFuncionario = repository.findByCpf(cpf);
        return optionalFuncionario.orElseThrow(() -> new ObjectNotFoundException("Funcionário não encontrado."));
    }

    // Lista todos
    public List<Funcionario> findAll() {
        List<Funcionario> listFuncionariosDB = repository.findAll();
        List<Funcionario> listActiveFuncionarios = new ArrayList<>();
        for (Funcionario funcionario : listFuncionariosDB) {
            if (funcionario.isStatus() == true) {
                listActiveFuncionarios.add(funcionario);
            }
        }

        return listActiveFuncionarios;
    }

    // Criar novo funcionário
    public Funcionario create(@Valid FuncionarioDto funcionarioDto) {
        // Valida CPF primeiro
        validaCpf(funcionarioDto);

        // Preparando objeto Endereço e persistindo
        Endereco endereco = new Endereco();
        endereco.setCep(funcionarioDto.getEndereco().getCep());
        endereco.setNumero(funcionarioDto.getEndereco().getNumero());
        endereco.setComplemento(funcionarioDto.getEndereco().getComplemento());
        endereco.setBairro(funcionarioDto.getEndereco().getBairro());
        endereco.setCidade(funcionarioDto.getEndereco().getCidade());
        endereco.setEstado(funcionarioDto.getEndereco().getEstado());
        endereco.setLogradouro(funcionarioDto.getEndereco().getLogradouro());

        // Preparando objeto UserFuncionario
        validaUsername(funcionarioDto.getUserFuncionario().getUsername());

        UserFuncionario userFuncionario = new UserFuncionario();
        userFuncionario.setUsername(funcionarioDto.getUserFuncionario().getUsername());
        userFuncionario.setPassword(funcionarioDto.getUserFuncionario().getPassword());

        // Preparando objeto Funcionario para persistência
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setEndereco(endereco);
        funcionario.setUserFuncionario(userFuncionario);
        funcionario.setDataCadastro(funcionarioDto.getDataCadastro());
        funcionario.setStatus(funcionarioDto.isStatus());
        funcionario.setDataNascimento(funcionarioDto.getDataNascimento());

        // Por padrão, o funcionário criado sempre terá o nível padrão
        funcionario.setNivelAutenticacao(NivelAutenticacao.PADRAO);

        // Preparando objeto Contato para persistência
        List<Contato> contatos = new ArrayList<>();
        for (Contato contato : funcionarioDto.getContatos()) {
            contato.setIdContato(null);
            contato.setPessoa(funcionario);
            contatos.add(contato);
        }
        funcionario.setContatos(contatos);

        // Salvando no banco
        return repository.save(funcionario);
    }

    public Funcionario update(Integer idPessoa, @Valid FuncionarioDto funcionarioDto) {
        // Buscar o funcionário existente no banco de dados
        Funcionario funcionarioDB = findById(idPessoa);

        // Verificar se o CPF foi alterado
        if (!funcionarioDto.getCpf().equals(funcionarioDB.getCpf())) {
            validaCpf(funcionarioDto);
        }

        // Atualizando usuário
        if (!funcionarioDto.getUserFuncionario().getUsername()
                .equals(funcionarioDB.getUserFuncionario().getUsername())) {
            validaUsername(funcionarioDto.getUserFuncionario().getUsername());
        }
        // Atualizando primeiros atributos do funcionário
        funcionarioDB.setNome(funcionarioDto.getNome());
        funcionarioDB.setDataNascimento(funcionarioDto.getDataNascimento());
        funcionarioDB.setStatus(funcionarioDto.isStatus());
        funcionarioDB.setDataCadastro(funcionarioDto.getDataCadastro());

        // Atualizando endereço
        funcionarioDB.getEndereco().setCep(funcionarioDto.getEndereco().getCep());
        funcionarioDB.getEndereco().setNumero(funcionarioDto.getEndereco().getNumero());
        funcionarioDB.getEndereco().setComplemento(funcionarioDto.getEndereco().getComplemento());
        funcionarioDB.getEndereco().setBairro(funcionarioDto.getEndereco().getBairro());
        funcionarioDB.getEndereco().setCidade(funcionarioDto.getEndereco().getCidade());
        funcionarioDB.getEndereco().setEstado(funcionarioDto.getEndereco().getEstado());
        funcionarioDB.getEndereco().setLogradouro(funcionarioDto.getEndereco().getLogradouro());

        funcionarioDB.getUserFuncionario().setUsername(funcionarioDto.getUserFuncionario().getUsername());
        funcionarioDB.getUserFuncionario().setPassword(funcionarioDto.getUserFuncionario().getPassword());

        // Atualizando contatos
        List<Contato> contatos = funcionarioDB.getContatos();
        for (Contato contato : funcionarioDto.getContatos()) {
            Optional<Contato> contatoExistente = funcionarioDB.getContatos().stream()
                    .filter(c -> c.getIdContato() == contato.getIdContato()).findFirst();

            if (contatoExistente.isPresent()) {
                contatoExistente.get().setNumero(contato.getNumero());
                contatoExistente.get().setTipo(contato.getTipo());
            } else {
                contato.setIdContato(null);
                contato.setPessoa(funcionarioDB);
                contatos.add(contato);
            }
        }
        funcionarioDB.setContatos(contatos);

        return repository.save(funcionarioDB);
    }

    public void delete(Integer idPessoa) {
        Funcionario funcionario = findById(idPessoa);
        funcionario.setStatus(false);
        repository.save(funcionario);
    }

    // Funções auxiliares
    public void validaCpf(FuncionarioDto funcionarioDto) {
        Optional<Funcionario> optionalFuncionario = repository.findByCpf(funcionarioDto.getCpf());
        if (optionalFuncionario.isPresent() && optionalFuncionario.get().getCpf() != funcionarioDto.getCpf()) {
            throw new DataIntegrityViolationException("CPF já cadastrado!");
        }
    }

    public void validaUsername(String username) {
        Optional<UserFuncionario> optionalUserFuncionario = userFuncionarioRepository.findByUsername(username);
        if (optionalUserFuncionario.isPresent()) {
            throw new DataIntegrityViolationException("Username já cadastrado!");
        }
    }

}
