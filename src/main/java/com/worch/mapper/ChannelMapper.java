package com.worch.mapper;

import com.worch.model.dto.response.ChannelResponseDto;
import com.worch.model.entity.Channel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChannelMapper {
    ChannelResponseDto toDto(Channel channel);

    default List<ChannelResponseDto> toDtoList(List<Channel> channels) {
        return channels.stream()
                .map(this::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
}