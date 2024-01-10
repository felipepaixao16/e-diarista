package com.project.ediarista.web.services;

import java.util.List;

import com.project.ediarista.core.exceptions.SenhasNaoConferemException;
import com.project.ediarista.core.exceptions.UsuarioJaCadastradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.ediarista.core.enums.TipoUsuario;
import com.project.ediarista.core.exceptions.UsuarioNaoEncontradoException;
import com.project.ediarista.core.models.Usuario;
import com.project.ediarista.core.repositories.UsuarioRepository;
import com.project.ediarista.web.dtos.UsuarioCadastroForm;
import com.project.ediarista.web.dtos.UsuarioEdicaoForm;
import com.project.ediarista.web.mappers.WebUsuarioMapper;
import org.springframework.validation.FieldError;

@Service
public class WebUsuarioService {
    
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private WebUsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario cadastrar(UsuarioCadastroForm form) {
        var senha = form.getSenha();
        var confirmacaoSenha = form.getConfirmacaoSenha();

        if(!senha.equals(confirmacaoSenha)) {
            var mensagem = "Os dois campos de senha não conferem";
            var fieldError = new FieldError(form.getClass().getName(), "confirmacaoSenha", form.getConfirmacaoSenha(),
                    false, null, null, mensagem);
            throw new SenhasNaoConferemException(mensagem, fieldError);
        }
        repository.findByEmail(form.getEmail()).ifPresent((usuario) -> {
            var mensagem = "Já existe um usuário cadastrado com esse e-mail";
            var fieldError = new FieldError(form.getClass().getName(), "email", form.getEmail(),
                    false, null, null, mensagem);
            throw new UsuarioJaCadastradoException(mensagem, fieldError);
        });

        var model = mapper.toModel(form);

        var senhaHash = passwordEncoder.encode(model.getSenha());

        model.setSenha(senhaHash);
        model.setTipoUsuario(TipoUsuario.ADMIN);

        validarCamposUnicos(model);
        
        return repository.save(model);
    }

    public Usuario buscarPorId(Long id) {
        var mensagem = String.format("Usuário com ID %d não encontrado", id);
        return repository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(mensagem));
    }

    public UsuarioEdicaoForm buscarFormPorId(Long id) {
        var usuario = buscarPorId(id);
        return mapper.toForm(usuario);
    }

    public Usuario editar(UsuarioEdicaoForm form, Long id) {
        var usuario = buscarPorId(id);

        var model = mapper.toModel(form);
        model.setId(usuario.getId());
        model.setSenha(usuario.getSenha());
        model.setTipoUsuario(usuario.getTipoUsuario());

        validarCamposUnicos(model);

        return repository.save(model);
    }

    public void excluirPorId(Long id) {
        var usuario = buscarPorId(id);
        repository.delete(usuario);
    }

    private void validarCamposUnicos(Usuario usuario) {
        if (repository.isEmailJaCadastrado(usuario.getEmail(), usuario.getId())) {
                var mensagem = "Já existe um usuário cadastrado com esse e-mail";
                var fieldError = new FieldError(usuario.getClass().getName(), "email", usuario.getEmail(),
                        false, null, null, mensagem);
                throw new UsuarioJaCadastradoException(mensagem, fieldError);
            }
    }
}
