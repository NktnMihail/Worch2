package com.worch.tests.service;

import com.worch.mapper.ChoiceMapper;
import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.entity.Choice;
import com.worch.model.enums.ChoiceStatus;
import com.worch.repository.ChoiceRepository;
import com.worch.service.ChoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChoiceServiceTest {

    @Mock
    private ChoiceRepository choiceRepository;

    @Mock
    private ChoiceMapper choiceMapper;

    @InjectMocks
    private ChoiceService choiceService;

    private Choice choice1;
    private Choice choice2;
    private ChoiceResponseDto dto1;
    private ChoiceResponseDto dto2;

    private static final UUID FIRST_CREATOR_ID = UUID.fromString("22bf6136-bb4a-4983-b3c2-1881fc6a400a");
    private static final UUID SECOND_CREATOR_ID = UUID.fromString("33bf6136-bb4a-4983-b3c2-1881fc6a400b");

    @BeforeEach
    void setUp() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime deadline = now.plusDays(7);

        choice1 = Choice.builder()
                .id(UUID.randomUUID())
                .creatorId(FIRST_CREATOR_ID)
                .title("First Choice")
                .personal(true)
                .status(ChoiceStatus.ACTIVE)
                .deadline(deadline)
                .createdAt(now)
                .build();

        choice2 = Choice.builder()
                .id(UUID.randomUUID())
                .creatorId(SECOND_CREATOR_ID)
                .title("Second Choice")
                .personal(false)
                .status(ChoiceStatus.ACTIVE)
                .deadline(deadline)
                .createdAt(now)
                .build();

        dto1 = ChoiceResponseDto.builder()
                .id(choice1.getId())
                .creatorId(choice1.getCreatorId())
                .channelId(choice1.getChannelId())
                .title(choice1.getTitle())
                .description(choice1.getDescription())
                .personal(choice1.isPersonal())
                .status(choice1.getStatus())
                .deadline(choice1.getDeadline())
                .createdAt(choice1.getCreatedAt())
                .build();

        dto2 = ChoiceResponseDto.builder()
                .id(choice2.getId())
                .creatorId(choice2.getCreatorId())
                .channelId(choice2.getChannelId())
                .title(choice2.getTitle())
                .description(choice2.getDescription())
                .personal(choice2.isPersonal())
                .status(choice2.getStatus())
                .deadline(choice2.getDeadline())
                .createdAt(choice2.getCreatedAt())
                .build();
        ;
    }

    @Test
    @DisplayName("Должен вернуть все чойсы когда creator_id не указан")
    void getAllChoices_shouldReturnAllWhenNoCreatorId() {

        List<Choice> allChoices = List.of(choice1, choice2);
        when(choiceRepository.findAll(any(Specification.class))).thenReturn(allChoices);
        when(choiceMapper.toDto(choice1)).thenReturn(dto1);
        when(choiceMapper.toDto(choice2)).thenReturn(dto2);

        List<ChoiceResponseDto> result = choiceService.getAllChoices(null);

        assertThat(result).hasSize(2);
        verify(choiceRepository).findAll(any(Specification.class));
    }

    @Test
    @DisplayName("Должен вернуть только чойсы указанного creator_id")
    void getAllChoices_shouldFilterByCreatorId() {

        List<Choice> creatorChoices = List.of(choice1);
        when(choiceRepository.findAll(any(Specification.class))).thenReturn(creatorChoices);
        when(choiceMapper.toDto(choice1)).thenReturn(dto1);

        List<ChoiceResponseDto> result = choiceService.getAllChoices(FIRST_CREATOR_ID);

        assertThat(result)
                .hasSize(1)
                .allMatch(dto -> dto.creatorId().equals(FIRST_CREATOR_ID));

        verify(choiceRepository).findAll(any(Specification.class));
        verify(choiceMapper).toDto(choice1);
    }

    @Test
    @DisplayName("Должен вернуть пустой список когда нет чойсов для creator_id")
    void getAllChoices_shouldReturnEmptyWhenNoChoicesForCreator() {

        UUID nonExistentCreatorId = UUID.randomUUID();
        when(choiceRepository.findAll(any(Specification.class))).thenReturn(List.of());

        List<ChoiceResponseDto> result = choiceService.getAllChoices(nonExistentCreatorId);

        assertThat(result).isEmpty();
        verify(choiceRepository).findAll(any(Specification.class));
        verifyNoInteractions(choiceMapper);
    }
}