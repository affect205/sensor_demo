package com.alexside.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SENSORS")
@Setter @Getter @NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="SUBJECT_ID")
    private Subject subject;

    @OneToMany(mappedBy = "sensor")
    private List<Event> events = new ArrayList<>();

    @Column
    private Double min;

    @Column
    private Double max;

    @Column
    private Boolean enabled;
}
