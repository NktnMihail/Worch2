package com.worch.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.worch.model.enums.ChoiceStatus;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record ChoiceResponseDto(
        UUID id,
        UUID creatorId,
        UUID channelId,
        String title,
        String description,
        @JsonProperty("isPersonal")
        boolean personal,
        ChoiceStatus status,
        ZonedDateTime deadline,
        ZonedDateTime createdAt
) {
}
