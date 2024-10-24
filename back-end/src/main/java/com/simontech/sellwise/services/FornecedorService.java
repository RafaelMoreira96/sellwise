package com.simontech.sellwise.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simontech.sellwise.domain.Fornecedor;
import com.simontech.sellwise.domain.Contato;
import com.simontech.sellwise.domain.Endereco;
import com.simontech.sellwise.domain.dtos.FornecedorDto;
import com.simontech.sellwise.repositories.FornecedorRepository;
import com.simontech.sellwise.services.exceptions.DataIntegrityViolationException;
import com.simontech.sellwise.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository repository;

    public Fornecedor findById(Integer id) {
        Optional<Fornecedor> optionalFornecedor = repository.findById(id);
        return optionalFornecedor
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado! ID: " + id));
    }

    public Fornecedor findByCnpj(String cnpj) {
        Optional<Fornecedor> optionalFornecedor = repository.findByCnpj(cnpj);
        return optionalFornecedor.orElseThrow(() -> new ObjectNotFoundException("Fornecedor não encontrado."));
    }

    public List<Fornecedor> findAll() {
        List<Fornecedor> listFornecedoresDB = repository.findAll();
        List<Fornecedor> listActiveFornecedores = new ArrayList<>();
        for (Fornecedor fornecedor : listFornecedoresDB) {
            if (fornecedor.isStatus() == true) {
                listActiveFornecedores.add(fornecedor);
            }
        }
        return listActiveFornecedores;
    }

    public Fornecedor create(@Valid FornecedorDto fornecedorDto) {
        validaCnpj(fornecedorDto);

        Endereco endereco = new Endereco();
        endereco.setCep(fornecedorDto.getEndereco().getCep());
        endereco.setNumero(fornecedorDto.getEndereco().getNumero());
        endereco.setComplemento(fornecedorDto.getEndereco().getComplemento());
        endereco.setBairro(fornecedorDto.getEndereco().getBairro());
        endereco.setCidade(fornecedorDto.getEndereco().getCidade());
        endereco.setEstado(fornecedorDto.getEndereco().getEstado());
        endereco.setLogradouro(fornecedorDto.getEndereco().getLogradouro());

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdPessoa(null);
        fornecedor.setCnpj(fornecedorDto.getCnpj());
        fornecedor.setEndereco(endereco);
        fornecedor.setRazaoSocial(fornecedorDto.getRazaoSocial());
        fornecedor.setNomeFantasia(fornecedorDto.getNomeFantasia());
        fornecedor.setInscricaoEstadual(fornecedorDto.getInscricaoEstadual());
        fornecedor.setStatus(fornecedorDto.isStatus());

        List<Contato> contatos = new ArrayList<>();
        for (Contato contato : fornecedorDto.getContatos()) {
            contato.setIdContato(null);
            contato.setPessoa(fornecedor);
            contatos.add(contato);
        }
        fornecedor.setContatos(contatos);

        return repository.save(fornecedor);
    }

    public Fornecedor update(Integer idPessoa, @Valid FornecedorDto fornecedorDto) {
        Fornecedor fornecedorDB = findById(idPessoa);

        if (!fornecedorDto.getCnpj().equals(fornecedorDB.getCnpj())) {
            validaCnpj(fornecedorDto);
        }

        fornecedorDB.setRazaoSocial(fornecedorDto.getRazaoSocial());
        fornecedorDB.setNomeFantasia(fornecedorDto.getNomeFantasia());
        fornecedorDB.setInscricaoEstadual(fornecedorDto.getInscricaoEstadual());
        fornecedorDB.setStatus(fornecedorDto.isStatus());

        List<Contato> contatos = fornecedorDB.getContatos();
        for (Contato contato : fornecedorDto.getContatos()) {
            Optional<Contato> contatoExistente = fornecedorDB.getContatos().stream()
                    .filter(c -> c.getIdContato().equals(contato.getIdContato()))
                    .findFirst();

            if (contatoExistente.isPresent()) {
                contatoExistente.get().setNumero(contato.getNumero());
                contatoExistente.get().setTipo(contato.getTipo());
            } else {
                contato.setIdContato(null);
                contato.setPessoa(fornecedorDB);
                contatos.add(contato);
            }
        }

        Endereco endereco = fornecedorDB.getEndereco();
        endereco.setCep(fornecedorDto.getEndereco().getCep());
        endereco.setNumero(fornecedorDto.getEndereco().getNumero());
        endereco.setComplemento(fornecedorDto.getEndereco().getComplemento());
        endereco.setBairro(fornecedorDto.getEndereco().getBairro());
        endereco.setCidade(fornecedorDto.getEndereco().getCidade());
        endereco.setEstado(fornecedorDto.getEndereco().getEstado());
        endereco.setLogradouro(fornecedorDto.getEndereco().getLogradouro());
        fornecedorDB.setEndereco(endereco);

        return repository.save(fornecedorDB);
    }

    public void delete(Integer id) {
        Fornecedor fornecedor = findById(id);
        fornecedor.setStatus(false);
        repository.save(fornecedor);
    }

    // Funções auxiliares
    public void validaCnpj(FornecedorDto fornecedorDto) {
        Optional<Fornecedor> obj = repository.findByCnpj(fornecedorDto.getCnpj());
        if (obj.isPresent() && obj.get().getCnpj() != fornecedorDto.getCnpj()) {
            throw new DataIntegrityViolationException("CNPJ já cadastrado!");
        }
    }
}
