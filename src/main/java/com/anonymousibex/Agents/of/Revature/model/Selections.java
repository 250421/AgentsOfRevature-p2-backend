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
    
    //@ManyToOne
    //@JoinColumn(name = "calamityId", referencedColumnName = "id")
    @Column(name = "calamityId")
    private Long calamityId;  // remember to change to type calamity

    @Column(name = "result")
    private String result;

    @Column(name = "hero1")
    private String hero1;
    
    @Column(name = "hero2")
    private String hero2;

    @Column(name = "hero3")
    private String hero3;
}
