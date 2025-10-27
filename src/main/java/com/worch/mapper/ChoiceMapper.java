package com.worch.mapper;

import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.entity.Choice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {
    ChoiceResponseDto toDto(Choice choice);
}
