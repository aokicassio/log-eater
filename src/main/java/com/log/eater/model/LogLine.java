package com.log.eater.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class LogLine {

    private String id;

    private State state;

    private String type;

    private String host;

    private long timestamp;

}
