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

    //@ManyToOne
    //@JoinColumn(name = "calamityId", referencedColumnName = "id")
    @Column(name = "calamityId")
    private Long calamityId;

    @Column(name = "result")
    private String result;

    @Column(name = "repGained")
    private int repGained;

    //@Column(name = "severity")    left here in case needed, should be able tp get it from join with calamities table
    //private String severity;



}
