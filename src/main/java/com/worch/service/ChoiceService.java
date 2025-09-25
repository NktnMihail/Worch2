package com.worch.service;

import com.worch.mapper.ChoiceMapper;
import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.entity.Choice;
import com.worch.model.specification.ChoiceSpecifications;
import com.worch.repository.ChoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceMapper choiceMapper;
    private final ChoiceRepository choiceRepository;

    public List<ChoiceResponseDto> getAllChoices(UUID creatorId){
        Specification<Choice> specification = Specification
                .where(ChoiceSpecifications.byCreatorId(creatorId));
        return choiceRepository.findAll(specification)
                .stream()
                .map(choiceMapper::toDto)
                .toList();
    }


}
