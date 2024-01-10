package com.project.ediarista.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.project.ediarista.core.models.Servico;
import com.project.ediarista.web.dtos.ServicoForm;

@Mapper(componentModel = "spring")
public interface WebServicoMapper {
    
    WebServicoMapper INSTANCE = Mappers.getMapper(WebServicoMapper.class);

    Servico toModel(ServicoForm form);

    ServicoForm toForm(Servico model);
}
