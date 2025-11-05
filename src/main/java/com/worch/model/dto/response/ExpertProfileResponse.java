package com.worch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "ExpertProfileResponse", description = "Профиль эксперта")
public record ExpertProfileResponse(
        @Schema(description = "Id", required = true)
        UUID id,

        @Schema(description = "Идентификатор пользователя", required = true)
        UUID userId,

        @Schema(description = "Имя пользователя", required = true)
        String userName,

        @Schema(description = "Статус инкогнито", required = true)
        Boolean isIncognito,

        @Schema(description = "цена", required = true)
        Integer price,

        @Schema(description = "Рейтинг", required = true)
        Float rating
) {}