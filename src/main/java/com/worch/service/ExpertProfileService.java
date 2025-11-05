package com.worch.service;

import com.worch.mapper.ExpertProfileMapper;
import com.worch.model.dto.response.ExpertProfileResponse;
import com.worch.model.entity.ExpertProfile;
import com.worch.model.entity.User;
import com.worch.repository.ExpertProfileRepository;
import com.worch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpertProfileService {

    private final UserRepository userRepository;
    private final ExpertProfileRepository expertProfileRepository;
    private final ExpertProfileMapper expertProfileMapper;

    public List<ExpertProfileResponse> findAll() {
        List<ExpertProfile> experts = expertProfileRepository.findAll();

        List<UUID> userIds = experts.stream()
                .map(ExpertProfile::getUserId)
                .toList();

        Map<UUID, User> usersMap = userRepository.findAllById(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        return experts.stream()
                .map(expert -> {
                    User user = usersMap.get(expert.getUserId());
                    return expertProfileMapper.toDto(expert, user);
                })
                .toList();
    }
}