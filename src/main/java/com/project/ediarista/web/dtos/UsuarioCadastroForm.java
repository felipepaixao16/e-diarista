package com.project.ediarista.web.dtos;

import com.project.ediarista.web.interfaces.IConfirmacaoSenha;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastroForm implements IConfirmacaoSenha {

    @NotNull
    @Size(min = 3, max = 225)
    private String nomeCompleto;

    @NotNull
    @Size(min = 3, max = 225)
    private String email;

    @NotNull
    @NotEmpty
    private String senha;

    @NotNull
    @NotEmpty
    private String confirmacaoSenha;
    
}
