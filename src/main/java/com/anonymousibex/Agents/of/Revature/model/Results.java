package com.anonymousibex.Agents.of.Revature.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calamityId", referencedColumnName = "id")
    private Calamity calamityId;

    @Column(name = "result")
    private String result;

    @Column(name = "repGained")
    private int repGained;

    //@Column(name = "severity")    left here in case needed, should be able tp get it from join with calamities table
    //private String severity;



}
