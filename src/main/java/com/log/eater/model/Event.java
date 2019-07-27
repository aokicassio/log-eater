package com.log.eater.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Entity
public class Event {

    @NotNull
    @Id
    @Column(name = "event_id")
    private String eventId;

    @NotNull
    @Column(name = "duration")
    private long duration;

    @Column(name = "type")
    private String type;

    @Column(name = "host")
    private String host;

    @NotNull
    @Column(name = "alert")
    private Boolean alert;

}
