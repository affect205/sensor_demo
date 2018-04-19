package com.alexside.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SENSOR_EVENTS")
@Setter @Getter @NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="SENSOR_ID")
    private Sensor sensor;

    @Column
    private Date utc;

    @Column
    private Double value;

    public Long getSensorId() {
        return sensor == null ? null : sensor.getId();
    }
}
