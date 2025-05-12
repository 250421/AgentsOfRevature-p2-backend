package com.anonymousibex.Agents.of.Revature.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "results")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "calamity_id", referencedColumnName = "id")
    private Calamity calamity;

    @Column(name = "calamity_id", insertable=false, updatable=false)
    private Long calamity_id;

    @Enumerated(EnumType.STRING)
    private MatchResult result;

    @Column(name = "repGained")
    private int repGained;
}
