package com.grpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "actors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private Boolean alive;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> awards;
}

