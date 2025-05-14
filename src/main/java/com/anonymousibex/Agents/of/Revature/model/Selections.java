package com.anonymousibex.Agents.of.Revature.model;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "selections")

public class Selections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "calamity_id", referencedColumnName = "id")
    private Calamity calamity;

    @Column(name = "calamity_id", insertable=false, updatable=false)
    private Long calamity_id;

    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long user_id;


    @Column(name = "hero1")
    private String hero1;
    
    @Column(name = "hero2")
    private String hero2;

    @Column(name = "hero3")
    private String hero3;
}
