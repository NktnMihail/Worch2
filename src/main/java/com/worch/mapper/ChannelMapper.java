package com.worch.mapper;


import com.worch.model.dto.response.ChannelResponseDto;
import com.worch.model.entity.Channel;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    ChannelResponseDto toDto(Channel channel);

    default List<ChannelResponseDto> toDtoList(List<Channel> channels) {
        return channels.stream()
                .map(this::toDto)
                .collect(java.util.stream.Collectors.toList());
    }

    // Метод конвертации OffsetDateTime ---> LocalDateTime
    default LocalDateTime map(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }
}
