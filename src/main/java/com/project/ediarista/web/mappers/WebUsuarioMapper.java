package com.project.ediarista.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.ediarista.core.models.Usuario;
import com.project.ediarista.web.dtos.UsuarioCadastroForm;
import com.project.ediarista.web.dtos.UsuarioEdicaoForm;

@Mapper(componentModel = "spring")
public interface WebUsuarioMapper {

    WebUsuarioMapper INSTANCE = Mappers.getMapper(WebUsuarioMapper.class);

    Usuario toModel(UsuarioCadastroForm form);
    Usuario toModel(UsuarioEdicaoForm form);

    UsuarioEdicaoForm toForm(Usuario model);
}
