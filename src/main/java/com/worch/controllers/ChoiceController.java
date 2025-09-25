package com.worch.controllers;

import com.worch.mapper.ChoiceMapper;
import com.worch.model.dto.response.ChoiceResponseDto;
import com.worch.model.entity.Choice;
import com.worch.repository.ChannelRepository;
import com.worch.repository.ChoiceRepository;
import com.worch.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/choices")
@RequiredArgsConstructor
public class ChoiceController {

    private final ChoiceRepository choiceRepository;
    private final ChoiceMapper choiceMapper;
    private final ChoiceService choiceService;

    @GetMapping
    public ResponseEntity<List<ChoiceResponseDto>> getChoices(
            @RequestParam(name = "creator_id", required = false) UUID creatorId)
    {
        return ResponseEntity.ok(choiceService.getAllChoices(creatorId));
    }
}
