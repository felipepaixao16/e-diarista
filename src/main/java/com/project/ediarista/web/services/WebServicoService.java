package com.project.ediarista.web.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ediarista.core.exceptions.ServicoNaoEncontradoException;
import com.project.ediarista.core.models.Servico;
import com.project.ediarista.core.repositories.ServicoRepository;
import com.project.ediarista.web.dtos.ServicoForm;
import com.project.ediarista.web.mappers.WebServicoMapper;

@Service
public class WebServicoService {
    
    @Autowired
    private ServicoRepository repository;

    @Autowired
    private WebServicoMapper mapper;

    public List<Servico> buscarTodos() {
        return repository.findAll();
    }

    public Servico cadastrar(ServicoForm form) {
        var model = mapper.toModel(form);
        return repository.save(model);
    }

    public Servico buscarPorId(Long id) {
        var servicoEncontrado = repository.findById(id);
        if (servicoEncontrado.isPresent()) {
            return servicoEncontrado.get();
        }
        var mensagem = String.format("Serviço com ID %d não encontrado", id);
        throw new ServicoNaoEncontradoException(mensagem);
    }

    public Servico editar(ServicoForm form, Long id) {
        var servicoEncontrado = buscarPorId(id);

        var model = mapper.toModel(form);
        model.setId(servicoEncontrado.getId());
        return repository.save(model);
    }

    public void excluirPorId(long id) {
        var servicoEncontrado = buscarPorId(id);
        repository.delete(servicoEncontrado);
    }
}
