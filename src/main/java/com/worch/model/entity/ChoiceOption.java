package com.worch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "choice_option")
@Getter
@Setter
public class ChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

    private String text;
    private Integer position;
}
