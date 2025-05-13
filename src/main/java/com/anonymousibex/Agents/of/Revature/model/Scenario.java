package com.anonymousibex.Agents.of.Revature.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "calamity_id")
    private Calamity calamity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private String hero1;
    private String hero2;
    private String hero3;

    private int pointTotal = 0;
    private int chapterCount = 0;
    private boolean complete = false;

    @Column(length = 1000)
    private String closing;

    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoryPoint> storyPoints = new ArrayList<>();

    @Transient
    public List<String> getHeroes() {
        return List.of(hero1, hero2, hero3);
    }
}
