package com.worch.tests.service;

import com.worch.mapper.ExpertProfileMapper;
import com.worch.model.dto.response.ExpertProfileResponse;
import com.worch.model.entity.ExpertProfile;
import com.worch.model.entity.User;
import com.worch.repository.ExpertProfileRepository;
import com.worch.repository.UserRepository;
import com.worch.service.ExpertProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Тест сервиса экспертов")
@ExtendWith(SpringExtension.class)
public class ExpertProfileServiceTest {

    @Mock
    private ExpertProfileRepository expertProfileRepository;
    @Mock
    private ExpertProfileMapper expertProfileMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ExpertProfileService expertProfileService;

    private ExpertProfile expertProfile1;
    private ExpertProfile expertProfile2;
    private User user1;
    private User user2;
    private ExpertProfileResponse response1;
    private ExpertProfileResponse response2;

    private ExpertProfile createExpertProfile(UUID id, UUID userId, Boolean isIncognito, Integer price, Float rating) {
        ExpertProfile expert = new ExpertProfile();
        expert.setId(id);
        expert.setUserId(userId);
        expert.setIsIncognito(isIncognito);
        expert.setPrice(price);
        expert.setRating(rating);
        return expert;
    }

    private User createUser(UUID id, String firstName, String lastName) {
        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .login("user" + id.toString().substring(0, 8))
                .password("password")
                .build();
    }

    @BeforeEach
    void setUp() {
        UUID expertId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
        UUID userId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174011");
        UUID expertId2 = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
        UUID userId2 = UUID.fromString("123e4567-e89b-12d3-a456-426614174022");

        expertProfile1 = createExpertProfile(expertId1, userId1, false, 1500, 4.7f);
        expertProfile2 = createExpertProfile(expertId2, userId2, true, 2000, 4.9f);

        user1 = createUser(userId1, "Иван", "Иванов");
        user2 = createUser(userId2, "Петр", "Петров");

        response1 = new ExpertProfileResponse(expertId1, userId1, "Иван Иванов", false, 1500, 4.7f);
        response2 = new ExpertProfileResponse(expertId2, userId2, "Петр Петров", true, 2000, 4.9f);
    }

    @Test
    @DisplayName("Должен возвращать все профили экспертов")
    void getAll_ShouldReturnAllExperts() {
    List<ExpertProfile> allExperts = List.of(expertProfile1, expertProfile2);
    List<User> allUsers = List.of(user1, user2);

        when(expertProfileRepository.findAll()).thenReturn(allExperts);
        when(userRepository.findAllById(anyList())).thenReturn(allUsers);
        when(expertProfileMapper.toDto(expertProfile1, user1)).thenReturn(response1);
        when(expertProfileMapper.toDto(expertProfile2, user2)).thenReturn(response2);

    List<ExpertProfileResponse> result = expertProfileService.findAll();

    assertThat(result).hasSize(2).containsExactly(response1, response2);
    verify(expertProfileRepository).findAll();
    verify(userRepository).findAllById(anyList());
    }

    @Test
    @DisplayName("Должен возвращать пустой список")
    void findAll_WhenNoExpertsExist_ShouldReturnEmptyList() {
        when(expertProfileRepository.findAll()).thenReturn(List.of());

        List<ExpertProfileResponse> result = expertProfileService.findAll();

        assertThat(result).isEmpty();
        verify(expertProfileRepository).findAll();
        verify(userRepository).findAllById(List.of());
        verifyNoInteractions(expertProfileMapper);
    }
}