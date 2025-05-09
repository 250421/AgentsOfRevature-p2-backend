package com.anonymousibex.Agents.of.Revature.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Selections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@ManyToOne
    //@JoinColumn(name = "calamityId", referencedColumnName = "id")
    //private Calamity calamityId;

    @Column(name = "result")
    private String result;

    @Column(name = "hero1")
    private String hero1;
    
    @Column(name = "hero2")
    private String hero2;

    @Column(name = "hero3")
    private String hero3;
}
