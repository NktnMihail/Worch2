package com.worch.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NamedEntityGraph(
        name = "Channel.withMembers",
        attributeNodes = @NamedAttributeNode(value = "members",
                subgraph = "membersWithUserAndRole"),
        subgraphs = @NamedSubgraph(
                name = "membersWithUserAndRole",
                attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("role")}
        )
)
@Entity
@Table(name = "channel")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private Boolean isPrivate;

    private String password;

    @Column(nullable = false)
    private Boolean ageRestricted;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private Set<ChannelUser> members;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}