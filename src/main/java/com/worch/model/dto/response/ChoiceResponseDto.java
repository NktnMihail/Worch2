package com.worch.model.dto.response;

import com.worch.model.enums.ChoiceStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ChoiceResponseDto(
        UUID id,
        UUID creatorId,
        UUID channelId,
        String title,
        String description,
        String image,
        boolean isPersonal,
        ChoiceStatus status,
        ZonedDateTime deadline,
        ZonedDateTime createdAt
) {
}
