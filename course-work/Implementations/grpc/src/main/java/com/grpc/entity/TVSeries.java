package com.grpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tv_series")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TVSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private Integer seasons;

    @Column(nullable = false)
    private Boolean ongoing;

    @Column(nullable = false)
    private Double rating;
}
