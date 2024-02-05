package com.argon.weather.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="NT_WEATHER")
public class Weather {

    @Id
    @Column(name = "WEATHER_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherId;

    @Column(name = "BASE_DATE", columnDefinition = "CHAR(14)", nullable = true)
    private String baseDate;

    @Column(name = "BASE_TIME", columnDefinition = "CHAR(14)", nullable = true)
    private String baseTime;

    @Column(name = "PTY", columnDefinition = "VARCHAR(50)", nullable = true)
    private String pty;

    @Column(name = "REH", columnDefinition = "VARCHAR(50)", nullable = true)
    private String reh;

    @Column(name = "RN1", columnDefinition = "VARCHAR(50)", nullable = true)
    private String rn1;

    @Column(name = "T1H", columnDefinition = "VARCHAR(50)", nullable = true)
    private String t1h;

    @Column(name = "UUU", columnDefinition = "VARCHAR(50)", nullable = true)
    private String uuu;

    @Column(name = "VEC", columnDefinition = "VARCHAR(50)", nullable = true)
    private String vec;

    @Column(name = "VVV", columnDefinition = "VARCHAR(50)", nullable = true)
    private String vvv;

    @Column(name = "WSD", columnDefinition = "VARCHAR(50)", nullable = true)
    private String wsd;

}
