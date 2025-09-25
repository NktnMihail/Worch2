package com.worch.mapper;

import com.worch.model.dto.response.ChannelResponseDto;
import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.entity.Channel;
import com.worch.model.entity.Choice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChoiceMapper {
    ChoiceResponseDto toDto(Choice choice);

    default List<ChoiceResponseDto> toDtoList(List<Choice> choices) {
        return choices.stream()
                .map(this::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
}
